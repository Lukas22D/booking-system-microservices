package com.sistemareseva.service_quartos.client.broker;

import org.springframework.stereotype.Service;
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
    
    private final QuartoService quartoService;
    private final Logger logger = LoggerFactory.getLogger(Consumer.class);


    @RabbitListener(queues = "rating-queue")
    public void consume(UpdateRatingRequest updateRating) throws Exception {
        logger.info("Received message: {}", updateRating);
        CompletableFuture.supplyAsync( () -> quartoService.updateRating(updateRating.idQuarto(), updateRating.rating()));

    }

}
