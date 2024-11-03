package com.sistemareserva.service_notification.client.feignClient.dto;

public record UserResponse(
    Long id,
    String email,
    String name,
    String phone
) {
    
}
