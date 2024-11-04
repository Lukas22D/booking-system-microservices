package com.sistemareserva.service_notification.client.rabbitmq.dto;

public record EmailDTO(
    String nomeHospede,
    String emailHospede,
    String dataCheckin,
    String dataCheckout,
    String TipoQuarto,
    String numeroReserva,
    String valorTotal,
    String status
) {
    
}
