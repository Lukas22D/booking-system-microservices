package com.sistemareserva.service_feedback.client.broker;

import org.springframework.stereotype.Service;

import com.sistemareserva.service_feedback.client.broker.dto.DeleteFeedBackByQuartoIdRequest;
import com.sistemareserva.service_feedback.service.FeedbackService;
import lombok.AllArgsConstructor;

import java.util.logging.Logger;

import org.springframework.amqp.rabbit.annotation.RabbitListener;

@AllArgsConstructor
@Service
public class Consumer {
    
    private final FeedbackService feedbackService;
    private final Logger logger = Logger.getLogger(Consumer.class.getName());

   @RabbitListener(queues = "deletefeedbacks-byQuartoId")
public void deleteFeedbacksByQuartoId(DeleteFeedBackByQuartoIdRequest deleteFeedBackByQuartoIdRequest) {
    try {
        feedbackService.deleteFeedbackByQuarto(deleteFeedBackByQuartoIdRequest.idQuarto());
    } catch (Exception e) {
        logger.warning("Error on deleteFeedbacksByQuartoId: " + e.getMessage());
    }
}




}
