package com.sistemareserva.service_reservas.infra.adapters.controller.dto;

import java.sql.Date;
import java.time.LocalDate;

public record CreateReservaRequest(
    Long idQuarto,
    Long idHospede,
    String dataEntrada, // Recebe como String no formato YYYY-MM-DD
    String dataSaida    // Recebe como String no formato YYYY-MM-DD
) {


    public Date parsedataEntrada(){
     return Date.valueOf(LocalDate.parse(dataEntrada));
    }

    public Date parsedataSaida(){
     return Date.valueOf(LocalDate.parse(dataSaida));
    }

}
