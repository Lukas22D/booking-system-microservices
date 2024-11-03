package com.sistemareserva.service_notification.client.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import com.sistemareserva.service_notification.client.feignClient.dto.UserResponse;

@FeignClient(name = "user-service", url = "http://localhost:8084/api/users")
public interface UserClient {
    
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(Long id);
}
