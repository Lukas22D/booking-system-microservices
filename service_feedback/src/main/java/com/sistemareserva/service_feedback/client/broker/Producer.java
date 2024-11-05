package com.sistemareserva.service_feedback.client.broker;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.sistemareserva.service_feedback.client.broker.dto.UpdateRatingRequest;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class Producer {
    
    private final RabbitTemplate rabbitTemplate;
    private final String exchange = "rating-exchange";

    public void sendUpdateRating(Long idQuarto, Double rating) {
        UpdateRatingRequest updateRatingRequest = new UpdateRatingRequest(idQuarto, rating);
        rabbitTemplate.convertAndSend(exchange, null, updateRatingRequest);
    }
}
