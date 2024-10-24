package com.sistemareseva.service_quartos.client.feightClient;

import com.sistemareseva.service_quartos.client.feightClient.dto.ReservaResponse;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "service-reservas", url = "localhost:8082/reservas")
public interface ReservasClient {

    @GetMapping("/quartos/reservados/{dataEntrada}/{dataSaida}")
     public ResponseEntity<List<Long>>  findQuartosReservados(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String dataEntrada,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String dataSaida);

    @GetMapping("/quarto/{idQuarto}")
    public ResponseEntity<ReservaResponse> findByQuartoId(@PathVariable Long idQuarto);
}
