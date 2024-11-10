package com.sistemareserva.service_notification.client.feignClient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import com.sistemareserva.service_notification.client.feignClient.dto.ReservaResponse;

@FeignClient(name = "reserva-service", url = "service-reservas:8082/reservas")
public interface ReservaClient {


     @GetMapping("/hospede/{idHospede}")
    public ResponseEntity<List<ReservaResponse>> findByIdHospede(@PathVariable Long idHospede);
}
