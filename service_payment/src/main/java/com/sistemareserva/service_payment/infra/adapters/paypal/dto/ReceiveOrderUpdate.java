package com.sistemareserva.service_payment.infra.adapters.paypal.dto;

public record ReceiveOrderUpdate(
    String id,
    String status
) {
    
}
