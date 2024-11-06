package com.sistemareserva.service_feedback.client.broker;

import java.math.BigDecimal;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.sistemareserva.service_feedback.client.broker.dto.UpdateRatingRequest;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class Producer {
    
    private final RabbitTemplate rabbitTemplate;
    private final String exchange = "feedback-exchanges";

    public void sendUpdateRating(Long idQuarto, BigDecimal  rating) {
        UpdateRatingRequest updateRatingRequest = new UpdateRatingRequest(idQuarto, rating);
        rabbitTemplate.convertAndSend(exchange, "updateRatting", updateRatingRequest);
    }
}
