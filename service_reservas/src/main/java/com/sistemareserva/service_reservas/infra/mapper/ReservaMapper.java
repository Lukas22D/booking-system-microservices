package com.sistemareserva.service_reservas.infra.mapper;

import com.sistemareserva.service_reservas.application.domain.ReservaEntity;
import com.sistemareserva.service_reservas.infra.adapters.persistence.repository.model.ReservaModel;

public class ReservaMapper {
    
    public static ReservaEntity toEntity(ReservaModel reserva) {
        ReservaEntity reservaEntity = new ReservaEntity(reserva.getId(), reserva.getIdQuarto(), reserva.getIdHospede(), reserva.getDataEntrada(), reserva.getDataSaida(), reserva.getValorTotal());
        reservaEntity.setStatus(reserva.getStatus());
        return reservaEntity;
    }

    public static ReservaModel toModel(ReservaEntity reserva) {
        return new ReservaModel(reserva.getId(), reserva.getIdQuarto(), reserva.getIdHospede(), reserva.getDataEntrada(), reserva.getDataSaida(), reserva.getUnidadeDiaria(), reserva.getStatus(), reserva.getQuantidadeDias(), reserva.getValorTotal());
    }

}
