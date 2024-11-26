package com.sistemareserva.service_payment.infra.gateways.payment.paypal.dto;

public record OrderResponse(
    String id,
    String link
) {
    
}
