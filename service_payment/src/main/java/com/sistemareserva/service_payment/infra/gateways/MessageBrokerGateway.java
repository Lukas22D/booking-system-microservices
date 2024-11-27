package com.sistemareserva.service_payment.infra.gateways;

import java.util.List;

import com.sistemareserva.service_payment.domain.entity.TransactionEntity;

public interface MessageBrokerGateway {
    
    void sendTransaction(List<TransactionEntity> transaction, String status);
}
