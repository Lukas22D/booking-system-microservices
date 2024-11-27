package com.sistemareserva.service_payment.infra.gateways;

import java.util.List;

import com.sistemareserva.service_payment.infra.db.model.Transaction;

public interface MessageBrokerGateway {
    
    void sendTransaction(List<Transaction> transaction, String status);
}
