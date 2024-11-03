package com.sistemareseva.service_user.controller.dto;

import com.sistemareseva.service_user.model.User;

public record UserResponse(
    Long id,
    String email,
    String name,
    String phone
) {

    public UserResponse(User model) {
        this(model.getId(), model.getEmail(), model.getName(), model.getPhone());
    }
    
}
