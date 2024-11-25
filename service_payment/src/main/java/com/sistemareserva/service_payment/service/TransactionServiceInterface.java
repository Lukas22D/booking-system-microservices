package com.sistemareserva.service_payment.service;

import java.util.concurrent.CompletableFuture;



public interface TransactionServiceInterface {

    public CompletableFuture<String> createOrder(Long idHospede);
}