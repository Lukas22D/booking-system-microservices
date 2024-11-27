package com.sistemareserva.service_payment.domain.service;

import java.util.concurrent.CompletableFuture;

public interface TransactionInterface {
    
    CompletableFuture<String> createOrder(Long idHospede);

    CompletableFuture<Void> updateStatusTransaction(String order);
}
