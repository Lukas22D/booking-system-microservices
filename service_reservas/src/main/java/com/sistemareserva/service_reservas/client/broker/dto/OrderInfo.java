package com.sistemareserva.service_reservas.client.broker.dto;

import java.util.List;

public record OrderInfo(
    List<Long> idReserva,
    String Status
) {
    
}
