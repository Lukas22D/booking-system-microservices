package com.sistemareserva.service_reservas.controller.dto;

import com.sistemareserva.service_reservas.model.Reservas;

public record ReservaResponse(
    Long id,
    Long idQuarto,
    Long idHospede,
    String dataEntrada,
    String dataSaida,
    String status
) {
    

    public ReservaResponse(Reservas reserva) {
        this(
            reserva.getId(),
            reserva.getIdQuarto(),
            reserva.getIdHospede(),
            reserva.getDataEntrada().toString(),
            reserva.getDataSaida().toString(),
            reserva.getStatus().toString()
        );
    }
    
}
