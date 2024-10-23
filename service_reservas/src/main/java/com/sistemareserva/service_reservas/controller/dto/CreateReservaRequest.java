package com.sistemareserva.service_reservas.controller.dto;

import java.time.LocalDate;
import java.sql.Date;

import com.sistemareserva.service_reservas.model.Reservas;

public record CreateReservaRequest(
    Long idQuarto,
    Long idHospede,
    String dataEntrada, // Recebe como String no formato YYYY-MM-DD
    String dataSaida    // Recebe como String no formato YYYY-MM-DD
) {

    public Reservas toModel() {
        // Converte a string para LocalDate, depois para java.sql.Date
        Date sqlDataEntrada = Date.valueOf(LocalDate.parse(dataEntrada));
        Date sqlDataSaida = Date.valueOf(LocalDate.parse(dataSaida));
        
        return new Reservas(idQuarto, idHospede, sqlDataEntrada, sqlDataSaida);
    }
}
