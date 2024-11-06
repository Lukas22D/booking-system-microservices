package com.sistemareseva.service_quartos.client.broker;

import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sistemareseva.service_quartos.client.broker.dto.UpdateRatingRequest;
import com.sistemareseva.service_quartos.service.QuartoService;

import lombok.AllArgsConstructor;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@AllArgsConstructor
@Service
public class Consumer {
    
    private final ObjectMapper mapper;
    private final QuartoService quartoService;
    private final Logger logger = LoggerFactory.getLogger(Consumer.class);


    @RabbitListener(queues = "rating-queue")
    public void consume(String message) throws Exception {
        var updateRating =mapper.readValue(message, UpdateRatingRequest.class);
        logger.info("Received message: {}", updateRating);
        CompletableFuture.supplyAsync( () -> quartoService.updateRating(updateRating.idQuarto(), updateRating.rating()));

    }

}
