package com.sistemareserva.service_reservas.infra.adapters.client.broker.dto;

import java.util.List;

public record OrderInfo(
    List<Long> idReserva,
    Long idHospede,
    String Status
) {
    
}
