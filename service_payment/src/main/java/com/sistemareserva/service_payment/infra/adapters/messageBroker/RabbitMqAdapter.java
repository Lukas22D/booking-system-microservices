package com.sistemareserva.service_payment.infra.adapters.messageBroker;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.sistemareserva.service_payment.infra.adapters.messageBroker.dto.OrderInfo;
import com.sistemareserva.service_payment.infra.db.model.Transaction;
import com.sistemareserva.service_payment.infra.gateways.MessageBrokerGateway;

import java.util.List;  

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class RabbitMqAdapter implements MessageBrokerGateway {
    
    private final RabbitTemplate rabbitTemplate;
    private final String exchange = "payment-exchange";
    
    @Override
    public void sendTransaction(List<Transaction> transaction, String status) {
        OrderInfo message = OrderInfo.fromTransaction(transaction, status);
        rabbitTemplate.convertAndSend(exchange, null, message);
    }
}
