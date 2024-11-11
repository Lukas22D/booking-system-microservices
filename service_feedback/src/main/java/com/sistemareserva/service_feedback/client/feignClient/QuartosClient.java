package com.sistemareserva.service_feedback.client.feignClient;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "quartos-service", url = "service-quartos:8081/quarto")
public interface QuartosClient  {
    
}
