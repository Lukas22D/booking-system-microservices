package com.sistemareserva.service_feedback.client.feignClient;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "quartos-service", url = "http://localhost:8081/quarto")
public interface QuartosClient  {
    
}
