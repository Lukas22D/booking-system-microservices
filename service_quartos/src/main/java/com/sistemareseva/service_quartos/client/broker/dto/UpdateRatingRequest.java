package com.sistemareseva.service_quartos.client.broker.dto;

public record UpdateRatingRequest(
    Long idQuarto,
    Double rating
) {
    
}
