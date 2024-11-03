package com.sistemareseva.service_user.controller.dto;

import com.sistemareseva.service_user.model.User;

public record UserRequest(
    String email,
    String password,
    String name,
    String phone,
    String cpf
) {

    public User toModel() {
        return new User(email, password, name, phone, cpf);
    }
    
}
