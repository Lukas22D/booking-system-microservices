package com.sistemareseva.service_user.service;

import java.util.Set;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sistemareseva.service_user.client.repository.UserRepository;
import com.sistemareseva.service_user.model.User;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserService {
    
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    public User createUser(User user) {
        repository.findByCpfOrPhoneOrEmail  
            (user.getCpf(), user.getPhone(), user.getEmail())
            .ifPresent((u) -> {
                throw new RuntimeException("User already exists");
            });
            user.setRoles(Set.of(roleService.getRole(1L)));
            user.setPassword(passwordEncoder.encode(user.getPassword()));


        return repository.save(user);
    }

    public void assignRoleToUser(Long userId, Long roleId) {
        // Recupera o usuário pelo ID
        var userOptional = repository.findById(userId).orElseThrow( () -> new RuntimeException("User not found"));
        // Recupera a role pelo ID
        var roleOptional = roleService.getRole(roleId);

        // Adiciona a role ao usuário
        userOptional.getRoles().add(roleOptional);
        // Salva o usuário
        repository.save(userOptional);
    }

    public User getUser(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));
    } 
    
    public User updateUser(Long id, User user) {
        return repository.findById(id)
            .map((u) -> {
                u.setEmail(user.getEmail());
                u.setName(user.getName());
                u.setPhone(user.getPhone());
                u.setCpf(user.getCpf());
                return repository.save(u);
            })
            .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void deleteUser(Long id) {
        repository.findById(id)
            .ifPresentOrElse((u) -> repository.delete(u),
                             () -> { throw new RuntimeException("User not found"); });
    }

}
