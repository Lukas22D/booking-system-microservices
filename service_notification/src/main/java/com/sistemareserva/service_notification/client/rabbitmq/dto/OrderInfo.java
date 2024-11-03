package com.sistemareserva.service_notification.client.rabbitmq.dto;

import java.util.List;

public record OrderInfo(
    List<Long> idReserva,
    Long idHospede,
    String Status
) {
    
}
