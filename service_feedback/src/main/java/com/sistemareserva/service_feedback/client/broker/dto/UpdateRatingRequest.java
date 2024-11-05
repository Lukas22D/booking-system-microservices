package com.sistemareserva.service_feedback.client.broker.dto;

public record UpdateRatingRequest(
    Long idQuarto,
    Double rating
) {
    
}
