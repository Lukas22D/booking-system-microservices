package com.sistemareserva.service_payment.client.broker;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.sistemareserva.service_payment.client.broker.dto.OrderInfo;
import com.sistemareserva.service_payment.model.Transaction;

import java.util.List;  

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ProducerRabbitMq {
    
    private final RabbitTemplate rabbitTemplate;
    private final String exchange = "payment-queue";
    
    public void send(List<Transaction> transaction, String status) {
        OrderInfo message = OrderInfo.fromTransaction(transaction, status);
        rabbitTemplate.convertAndSend(exchange, message);
    }
}
