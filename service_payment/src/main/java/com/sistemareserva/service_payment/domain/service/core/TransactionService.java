package com.sistemareserva.service_payment.domain.service.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.sistemareserva.service_payment.domain.handler.error.OrderCreateException;
import com.sistemareserva.service_payment.domain.service.TransactionInterface;
import com.sistemareserva.service_payment.infra.adapters.persistence.db.model.enums.TransactionStatus;
import com.sistemareserva.service_payment.domain.entity.OrderEntity;
import com.sistemareserva.service_payment.domain.entity.TransactionEntity;
import com.sistemareserva.service_payment.infra.gateways.TransactionRepository;
import com.sistemareserva.service_payment.infra.gateways.PaymentGateway;
import com.sistemareserva.service_payment.infra.gateways.ReservasGateway;
import com.sistemareserva.service_payment.infra.gateways.MessageBrokerGateway;

import jakarta.persistence.EntityNotFoundException;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class TransactionService implements TransactionInterface {

    private final TransactionRepository repository;
    private final PaymentGateway paymenteInterface;
    private final ReservasGateway reservasClient;
    private final Logger logger = LoggerFactory.getLogger(TransactionService.class);
    public final MessageBrokerGateway brokerOrder;

    public TransactionService(TransactionRepository repository, PaymentGateway paymenteInterface, ReservasGateway reservasClient, MessageBrokerGateway brokerOrder) {
        this.repository = repository;
        this.paymenteInterface = paymenteInterface;
        this.reservasClient = reservasClient;
        this.brokerOrder = brokerOrder;
    }

    @Override
    @Async
    public CompletableFuture<String> createOrder(Long idHospede) {

        // Obtém a reserva de forma assíncrona
        CompletableFuture<List<OrderEntity>> order = CompletableFuture
                .completedFuture(reservasClient.getReserva(idHospede))
                .thenCompose(or -> {
                    // Verifica se a reserva foi encontrada
                    if (or == null) {
                        // Loga e lança exceção caso não encontre a reserva
                        logger.error("Reserva não encontrada para o hospede: " + idHospede);
                        throw new EntityNotFoundException("Reserva não encontrada para o hospede: " + idHospede);
                    }
                    // Cria o pedido de forma assíncrona
                    return paymenteInterface.createOrder(or)
                            .exceptionally(e -> {
                                logger.error("Erro ao criar pedido: " + e.getMessage());
                                throw new OrderCreateException(e.getMessage());
                            });
                });

        // Processa o pedido e salva a transação de forma assíncrona
        return order.thenApply(o -> {
            // Chama saveTransaction de forma assíncrona
            saveTransaction(o);
            // Retorna o LinkOrder do primeiro pedido
            return o.get(0).getLinkOrder();
        });
    }

    public CompletableFuture<Void> saveTransaction(List<OrderEntity> request) {

        return CompletableFuture.runAsync(() -> {
            List<TransactionEntity> transactions = request.stream()
                    .map(reserva -> new TransactionEntity(
                            null,
                            Long.valueOf(reserva.getIdReserva()),
                            Integer.parseInt(reserva.getQuantidadeDias()),
                            reserva.getIdOrder(),
                            Long.valueOf(reserva.getIdHospede()),
                            new BigDecimal(reserva.getValorTotal())))
                    .collect(Collectors.toList());
            brokerOrder.sendTransaction(transactions, "PENDING");
            repository.saveAll(transactions);
            logger.info("Transações salvas: " + transactions.size() + " registros.");
        }).exceptionally(e -> {
            logger.error("Erro ao salvar transações: " + e.getMessage());
            throw new OrderCreateException(e.getMessage());
        });
    }

    @Override
    @Async
    public CompletableFuture<Void> updateStatusTransaction(String order) {
        // Obtém a atualização do pedido
        var orderReceive = paymenteInterface.OrderRecive(order);

        // Obtém as transações a serem atualizadas
        List<TransactionEntity> transactionUpdate = repository.findByIdPagamento(orderReceive.id());

        // Envia a atualização do pedido para o broker
        brokerOrder.sendTransaction(transactionUpdate, orderReceive.status());

        // Simula a lógica de atualização usando o status e id
        if ("APPROVED".equals(orderReceive.status())) {
            transactionUpdate.forEach(transaction -> {
                transaction.setStatus(TransactionStatus.APPROVED);
                repository.save(transaction);
                logger.info("Transação com ID: " + transaction.getId() + " atualizada com sucesso. Status: "
                        + transaction.getStatus());
            });
        } else {
            transactionUpdate.forEach(transaction -> {
                transaction.setStatus(TransactionStatus.REJECTED);
                repository.save(transaction);
                logger.info("Transação com ID: " + transaction.getId() + " atualizada com sucesso. Status: "
                        + transaction.getStatus());
            });

        }

        return CompletableFuture.completedFuture(null);
    }

}
