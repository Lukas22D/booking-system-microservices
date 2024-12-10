package com.sistemareserva.service_reservas.infra.adapters.client.feignClient;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.sistemareserva.service_reservas.infra.adapters.client.feignClient.dto.QuartosReponse;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "quartos", url = "service-quartos:8081/quarto")
public interface QuartosClient {

    @GetMapping("/{id}")
    ResponseEntity<QuartosReponse> getQuartoById(@PathVariable("id") Long id);
}

