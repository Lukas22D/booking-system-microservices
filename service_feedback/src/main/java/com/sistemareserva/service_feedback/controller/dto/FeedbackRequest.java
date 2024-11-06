package com.sistemareserva.service_feedback.controller.dto;

import java.math.BigDecimal;

import com.sistemareserva.service_feedback.model.Feedback;

public record FeedbackRequest(
    Long idQuarto,
    BigDecimal  rating,
    String feedback,
    Long idUser
) {

    public Feedback toModel(){
        return new Feedback(feedback, rating, idQuarto, idUser);
    }
    
}
