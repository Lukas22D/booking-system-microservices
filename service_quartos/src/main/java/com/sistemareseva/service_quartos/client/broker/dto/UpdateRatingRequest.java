package com.sistemareseva.service_quartos.client.broker.dto;

import java.math.BigDecimal;

public record UpdateRatingRequest(
    Long idQuarto,
    BigDecimal rating
) {
    
}
