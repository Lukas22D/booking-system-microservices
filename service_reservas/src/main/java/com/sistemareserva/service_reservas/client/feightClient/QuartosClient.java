package com.sistemareserva.service_reservas.client.feightClient;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.cloud.openfeign.FeignClient;
import com.sistemareserva.service_reservas.client.feightClient.dto.QuartosReponse;

@FeignClient(name = "quartos", url = "http://localhost:8081/quarto")
public interface QuartosClient {

    @GetMapping("/{id}")
    ResponseEntity<QuartosReponse> getQuartoById(@PathVariable("id") Long id);
}

