package com.sistemareseva.service_user.service;

import com.sistemareseva.service_user.model.Role;
import com.sistemareseva.service_user.client.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public void initRoles() {
        if (roleRepository.findByName("ADMIN").isEmpty()) {
            roleRepository.save(new Role("ADMIN"));
        }
        if (roleRepository.findByName("USER").isEmpty()) {
            roleRepository.save(new Role("USER"));
        }
    }

    public Role getRole(Long id) {
        return roleRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Role not found"));
    }
}
