package com.sistemareseva.service_user.model;

import java.util.Set;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "tb_users_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    public User(String email, String password, String name, String phone, String cpf) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.cpf = cpf;
    }
    
}
