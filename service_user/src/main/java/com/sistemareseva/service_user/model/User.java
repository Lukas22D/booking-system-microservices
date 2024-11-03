package com.sistemareseva.service_user.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "tb_users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    private String name;

    @Column(unique = true)  
    private String phone;

    @Column(unique = true)
    private String cpf;

    public User(String email, String password, String name, String phone, String cpf) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.cpf = cpf;
    }
    
}
