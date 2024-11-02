package com.sistemareserva.service_payment.client.FaightClient;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.sistemareserva.service_payment.client.FaightClient.dto.ReservaResponse;

@FeignClient(name = "service-reservas", url = "localhost:8082/reservas")
public interface ReservasClient {

    @GetMapping("/hospede/{idHospede}")
    public ResponseEntity<List<ReservaResponse>> findByIdHospede(@PathVariable Long idHospede);
}
