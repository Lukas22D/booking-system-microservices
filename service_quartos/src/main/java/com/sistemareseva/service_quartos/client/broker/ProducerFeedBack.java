package com.sistemareseva.service_quartos.client.broker;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.sistemareseva.service_quartos.client.broker.dto.DeleteFeedBackByQuartoIdRequest;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ProducerFeedBack {

    private final RabbitTemplate rabbitTemplate;
    private final String exchange = "feedback-exchanges";
    private final String routingKey = "delete";

    public void sendToDeleteFeedBack(Long idReserva) {
        DeleteFeedBackByQuartoIdRequest deleteFeedBackByQuartoIdRequest = new DeleteFeedBackByQuartoIdRequest(idReserva);
        rabbitTemplate.convertAndSend(exchange, routingKey, deleteFeedBackByQuartoIdRequest);
    }
}
