package com.sistemareserva.service_payment.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sistemareserva.service_payment.client.provider.FaightClient.ReservasClient;
import com.sistemareserva.service_payment.client.provider.FaightClient.dto.ReservaResponse;
import com.sistemareserva.service_payment.client.provider.PayPal.PayPalService;
import com.sistemareserva.service_payment.client.provider.PayPal.dto.OrderRequest;
import com.sistemareserva.service_payment.client.provider.PayPal.dto.OrderResponse;
import com.sistemareserva.service_payment.client.provider.broker.ProducerRabbitMq;
import com.sistemareserva.service_payment.client.provider.repository.TransactionRepository;
import com.sistemareserva.service_payment.model.Transaction;
import com.sistemareserva.service_payment.model.enums.TransactionStatus;

import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class TransactionService {

    private final TransactionRepository repository;
    private final PayPalService payPalService;
    private final ReservasClient reservasClient;
    private final ObjectMapper mapper;
    private final Logger logger = LoggerFactory.getLogger(TransactionService.class);
    public final ProducerRabbitMq brokerOrder;

    @Async
    public CompletableFuture<String> createOrder(Long idHospede) {
        // Obtém a reserva de forma assíncrona
        CompletableFuture<List<ReservaResponse>> reserva = CompletableFuture
                .completedFuture(reservasClient.findByIdHospede(idHospede))
                .thenApply(responseEntity -> {
                    List<ReservaResponse> reservas = responseEntity.getBody();
                    if (reservas == null || reservas.isEmpty()) {
                        throw new RuntimeException("Nenhuma reserva encontrada para o hóspede com ID: " + idHospede);
                    }
                    return reservas;
                });
        // Cria o pedido PayPal com a reserva
        CompletableFuture<OrderRequest> orderRequest = reserva.thenApply(OrderRequest::new);

        // Processa o pedido PayPal e obtém a resposta
        CompletableFuture<OrderResponse> orderResponse = orderRequest.thenCompose(or -> {
            return payPalService.createOrder(or)
                    .exceptionally(e -> {
                        logger.error("Erro ao criar pedido: " + e.getMessage());
                        throw new RuntimeException("Falha ao criar pedido", e);
                    });
        });

        // Inicia a operação de salvar transações de forma assíncrona
        orderResponse.thenAcceptBoth(reserva, (response, reservasList) -> {
            saveTransaction(reservasList, response.id())
                    .exceptionally(e -> {
                        logger.error("Erro ao salvar transações: " + e.getMessage());
                        throw new RuntimeException("Falha ao salvar transações", e);
                    });
        });
        // Retorna o link de aprovação do pedido P-ayPal imediatamente
        return orderResponse.thenApply(OrderResponse::link);
    }

    public CompletableFuture<Void> saveTransaction(List<ReservaResponse> request, String orderId) {
        return CompletableFuture.runAsync(() -> {
            List<Transaction> transactions = request.stream()
                    .map(reserva -> new Transaction(
                            Long.valueOf(reserva.id()),
                            Integer.parseInt(reserva.quantidadeDias()),
                            orderId,
                            Long.valueOf(reserva.idHospede()),
                            new BigDecimal(reserva.valorTotal())))
                    .collect(Collectors.toList());
            brokerOrder.send(transactions, "PENDING");
            repository.saveAll(transactions);
            logger.info("Transações salvas: " + transactions.size() + " registros.");
        }).exceptionally(e -> {
            logger.error("Erro ao salvar transações: " + e.getMessage());
            throw new RuntimeException("Falha ao salvar transações", e);
        });
    }


    @Async
    public CompletableFuture<Void> updateStatusTransaction(String order){

        try {
            // Cria o ObjectMapper para ler o JSON
            JsonNode root = mapper.readTree(order);

            // Extrai "status" e "id" da resposta JSON
            String status = root.path("resource").path("status").asText();
            String id = root.path("resource").path("id").asText();

            List<Transaction> transactionUpdate = repository.findByIdPagamento(id);
            brokerOrder.send(transactionUpdate, status);
            // Simula a lógica de atualização usando o status e id
            if ("APPROVED".equals(status)) {
                transactionUpdate.forEach(transaction -> {
                    transaction.setStatus(TransactionStatus.APPROVED);
                    repository.save(transaction);
                    logger.info("Transação com ID: " + transaction.getId() + " atualizada com sucesso. Status: " + transaction.getStatus());
                });
            } else {
                transactionUpdate.forEach(transaction -> {
                    transaction.setStatus(TransactionStatus.REJECTED);
                    repository.save(transaction);
                    logger.info("Transação com ID: " + transaction.getId() + " atualizada com sucesso. Status: " + transaction.getStatus());
                });
                
            }

        } catch (Exception e) {
            // Tratamento de exceção
            logger.error("Erro ao atualizar transação: " + e.getMessage());
            throw new RuntimeException("Falha ao atualizar transação", e);

        }

        return CompletableFuture.completedFuture(null);
    }


}
