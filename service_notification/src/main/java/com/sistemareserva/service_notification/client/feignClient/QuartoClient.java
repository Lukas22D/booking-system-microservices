package com.sistemareserva.service_notification.client.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.sistemareserva.service_notification.client.feignClient.dto.QuartosReponse;

@FeignClient(name = "quarto-service", url = "http://localhost:8081/quarto")
public interface QuartoClient {
    
    @GetMapping("/{id}")
    public ResponseEntity<QuartosReponse> getQuartoById(@PathVariable("id") Long id);
}
