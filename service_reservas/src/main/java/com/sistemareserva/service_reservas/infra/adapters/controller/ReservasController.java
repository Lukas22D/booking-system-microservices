package com.sistemareserva.service_reservas.infra.adapters.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistemareserva.service_reservas.application.service.ReservaService;
import com.sistemareserva.service_reservas.infra.adapters.controller.dto.CreateReservaRequest;
import com.sistemareserva.service_reservas.infra.adapters.controller.dto.ReservaResponse;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/reservas")
public class ReservasController {

    private final ReservaService service;

    @PostMapping
    public CompletableFuture<ResponseEntity<ReservaResponse>> createdReserva(CreateReservaRequest request) {
        CompletableFuture<ReservaResponse> reserva = service.saveReserva(request.idQuarto(), request.idHospede(), request.parsedataEntrada(), request.parsedataSaida() ).thenApply(ReservaResponse::new);
        return reserva
                .thenApply(reservaSaved -> ResponseEntity.status(HttpStatus.CREATED).body(reservaSaved));
    }

    @GetMapping("/quartos/reservados/{dataEntrada}/{dataSaida}")
    public ResponseEntity<List<Long>> findQuartosReservados(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String dataEntrada,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String dataSaida) {

        Date sqlDataEntrada = Date.valueOf(LocalDate.parse(dataEntrada));
        Date sqlDataSaida = Date.valueOf(LocalDate.parse(dataSaida));

        return ResponseEntity.ok(service.findQuartosReservados(sqlDataEntrada, sqlDataSaida));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReserva(@PathVariable Long id) {
        service.deleteReserva(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservaResponse> findReservaById(@PathVariable Long id) {
        var reserva = Optional.ofNullable(service.findReservaById(id))
                              .map(ReservaResponse::new)
                              .orElse(null);

        return ResponseEntity.ok(reserva);
    }

    @GetMapping("/hospede/{idHospede}/status/{status}")
    public ResponseEntity<List<ReservaResponse>> findByIdHospedeAndStatus(@PathVariable Long idHospede, @PathVariable String status) {
          return ResponseEntity.ok(service.findByIdHospedeAndStatus(idHospede, status).stream().map(ReservaResponse::new).collect(Collectors.toList()));
                
    }

    @GetMapping("/hospede/{idHospede}/status/null")
    public ResponseEntity<List<ReservaResponse>> findByIdHospedeAndStatusNull(@PathVariable Long idHospede) {
        return ResponseEntity.ok(service.findByIdHospedeAndStatusNull(idHospede).stream().map(ReservaResponse::new).collect(Collectors.toList()));
    }

    @GetMapping("/hospede/{idHospede}")
    public CompletableFuture<ResponseEntity<List<ReservaResponse>>> findByIdHospede(@PathVariable Long idHospede) {
       return  service.findByIdHospede(idHospede)
               .thenApply(reservas -> ResponseEntity.ok(reservas.stream().map(ReservaResponse::new).collect(Collectors.toList())));
    }


    @GetMapping("/quarto/{idQuarto}")
    public ResponseEntity<ReservaResponse> findByQuartoId(@PathVariable Long idQuarto) {
    var reserva = service.findByQuartoId(idQuarto);
    
    if (reserva == null) {
        // Retorna 404 se nenhuma reserva for encontrada
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
    
    // Se a reserva for encontrada, cria a resposta normalmente
    ReservaResponse reservaResponse = new ReservaResponse(reserva);
    return ResponseEntity.ok(reservaResponse);
}

}
