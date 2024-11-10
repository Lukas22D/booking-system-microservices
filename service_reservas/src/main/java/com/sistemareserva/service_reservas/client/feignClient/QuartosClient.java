package com.sistemareserva.service_reservas.client.feignClient;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.cloud.openfeign.FeignClient;
import com.sistemareserva.service_reservas.client.feignClient.dto.QuartosReponse;

@FeignClient(name = "quartos", url = "service-quartos:8081/quarto")
public interface QuartosClient {

    @GetMapping("/{id}")
    ResponseEntity<QuartosReponse> getQuartoById(@PathVariable("id") Long id);
}

