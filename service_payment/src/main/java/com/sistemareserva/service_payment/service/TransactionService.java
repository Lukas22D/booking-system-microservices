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
import com.sistemareserva.service_payment.client.provider.repository.TransactionRepository;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@AllArgsConstructor
@Service
public class TransactionService {

    private final TransactionRepository repository;
    private final PayPalService payPalService;
    private final ReservasClient reservasClient;
    private final Logger logger = LoggerFactory.getLogger(TransactionService.class);
        

    @Async
    public CompletableFuture<String> createOrder(Long idHospede) throws Exception {
        CompletableFuture<List<ReservaResponse>> reserva = CompletableFuture
                .completedFuture(reservasClient.findByIdHospede(idHospede))
                .thenApply(ResponseEntity::getBody);
                

        CompletableFuture<OrderRequest> orderRequest = reserva.thenApply(OrderRequest::new);

        return orderRequest.thenApply( or -> {
                try {
                    return payPalService.createOrder(or).get();
                } catch (Exception e) {
                    logger.error("Erro ao criar pedido: " + e.getMessage());
                    throw new RuntimeException(e);
                }
        });
    };

    
}
