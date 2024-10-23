package com.sistemareserva.service_reservas.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistemareserva.service_reservas.controller.dto.CreateReservaRequest;
import com.sistemareserva.service_reservas.model.Reservas;
import com.sistemareserva.service_reservas.service.ReservaService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/reservas")
public class ReservasController {

    private final ReservaService service;

    @PostMapping
    public ResponseEntity<Reservas> createdReserva(CreateReservaRequest request) {
        Reservas reserva = request.toModel();
        service.saveReserva(reserva.getIdQuarto(), reserva.getIdHospede(), reserva.getDataEntrada(),
                reserva.getDataSaida());
        return ResponseEntity.ok(reserva);
    }

    @GetMapping("/quartos/reservados/{dataEntrada}/{dataSaida}")
    public ResponseEntity<List<Long>> findQuartosReservados(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String dataEntrada,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String dataSaida) {

        Date sqlDataEntrada = Date.valueOf(LocalDate.parse(dataEntrada));
        Date sqlDataSaida = Date.valueOf(LocalDate.parse(dataSaida));

        return ResponseEntity.ok(service.findQuartosReservados(sqlDataEntrada, sqlDataSaida));
    }

}
