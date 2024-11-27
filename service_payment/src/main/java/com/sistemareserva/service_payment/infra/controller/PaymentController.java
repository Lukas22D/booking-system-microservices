package com.sistemareserva.service_payment.infra.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistemareserva.service_payment.domain.service.TransactionService;

import java.util.concurrent.CompletableFuture;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/paypal")
@CrossOrigin(origins = "*") // Adiciona a configuração de CORS para o controlador
public class PaymentController {

    private final TransactionService transactionService;
    private final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    @GetMapping("/create-order/{idHospede}")
    public CompletableFuture<ResponseEntity<String>> createOrder(@PathVariable("idHospede") Long idHospede)
            throws Exception {
        return transactionService.createOrder(idHospede).thenApply(ResponseEntity::ok);
    }

    @PostMapping("/capture-order")
    public CompletableFuture<ResponseEntity<Object>> captureOrder(@RequestBody String orderId) {
        logger.info("Capturando pedido: " + orderId);
        return transactionService.updateStatusTransaction(orderId)
                .thenApply(result -> ResponseEntity.ok().build())
                .exceptionally(e -> {
                    logger.error("Erro ao capturar pedido: " + e.getMessage());
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                });
    }



}