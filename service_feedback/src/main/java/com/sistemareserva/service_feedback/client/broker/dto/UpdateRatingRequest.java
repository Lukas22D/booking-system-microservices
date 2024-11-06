package com.sistemareserva.service_feedback.client.broker.dto;

import java.math.BigDecimal;

public record UpdateRatingRequest(
    Long idQuarto,
    BigDecimal  rating
) {
    
}
