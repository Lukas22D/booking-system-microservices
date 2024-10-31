package com.sistemareserva.service_payment.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.sistemareserva.service_payment.client.provider.FaightClient.ReservasClient;
import com.sistemareserva.service_payment.client.provider.FaightClient.dto.ReservaResponse;
import com.sistemareserva.service_payment.client.provider.PayPal.PayPalService;
import com.sistemareserva.service_payment.client.provider.PayPal.dto.OrderRequest;
import com.sistemareserva.service_payment.client.provider.PayPal.dto.OrderResponse;
import com.sistemareserva.service_payment.client.provider.repository.TransactionRepository;
import com.sistemareserva.service_payment.model.Transaction;

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
    private final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    @Async
    public CompletableFuture<String> createOrder(Long idHospede) {
        // Obtém a reserva de forma assíncrona
        CompletableFuture<List<ReservaResponse>> reserva = CompletableFuture
                .completedFuture(reservasClient.findByIdHospede(idHospede))
                .thenApply(ResponseEntity::getBody);

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

        // Retorna o link de aprovação do pedido PayPal imediatamente
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

                repository.saveAll(transactions);
                logger.info("Transações salvas: " + transactions.size() + " registros.");
        }).exceptionally(e -> {
            logger.error("Erro ao salvar transações: " + e.getMessage());
            throw new RuntimeException("Falha ao salvar transações", e);
        });
    }
}
