package com.sistemareserva.service_categoria.service;

import com.sistemareserva.service_categoria.model.Category;

import org.springframework.stereotype.Service;

import com.sistemareserva.service_categoria.client.repository.CategoryRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository repository;

    public Category createCategory(Category category) {
        return repository.save(category);
    }
    
    
}
