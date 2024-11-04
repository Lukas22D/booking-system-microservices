package com.sistemareseva.service_user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import com.sistemareseva.service_user.controller.dto.UserRequest;
import com.sistemareseva.service_user.controller.dto.UserResponse;
import com.sistemareseva.service_user.service.UserService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {
    
    private final UserService service;

    @PostMapping
    public ResponseEntity<UserResponse> createdUser(@RequestBody UserRequest request){
        var user = service.createUser(request.toModel());
        return ResponseEntity.ok(new UserResponse(user));
    };

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable("id") Long id){
        var user = service.getUser(id);
        return ResponseEntity.ok(new UserResponse(user));
    }

    @PostMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable("id") Long id, @RequestBody UserRequest request){
        var user = service.updateUser(id, request.toModel());
        return ResponseEntity.ok(new UserResponse(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id){
        service.deleteUser(id);
        return ResponseEntity.ok().build();
    }

}
