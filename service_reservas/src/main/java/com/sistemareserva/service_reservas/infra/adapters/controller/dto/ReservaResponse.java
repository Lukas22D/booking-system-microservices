package com.sistemareserva.service_reservas.infra.adapters.controller.dto;

import com.sistemareserva.service_reservas.application.domain.ReservaEntity;

public record ReservaResponse(
    Long id,
    Long idQuarto,
    Long idHospede,
    String dataEntrada,
    String dataSaida,
    String valorTotal,
    String quantidadeDias,
    String UnidadeDiaria
) {
    

    public ReservaResponse(ReservaEntity reserva) {
        this(
            reserva.getId(),
            reserva.getIdQuarto(),
            reserva.getIdHospede(),
            reserva.getDataEntrada().toString(),
            reserva.getDataSaida().toString(),
            reserva.getValorTotal().toString(),
            reserva.getQuantidadeDias().toString(),
            reserva.getUnidadeDiaria().toString()
        );
    }
    
}
