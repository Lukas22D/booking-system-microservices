package com.sistemareserva.service_payment.client.feignClient;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.sistemareserva.service_payment.client.feignClient.dto.ReservaResponse;

@FeignClient(name = "service-reservas", url = "service-reservas:8082/reservas")
public interface ReservasClient {

    @GetMapping("/hospede/{idHospede}/status/null")
    public ResponseEntity<List<ReservaResponse>> findByIdHospedeAndStatusNull(@PathVariable Long idHospede);
}
