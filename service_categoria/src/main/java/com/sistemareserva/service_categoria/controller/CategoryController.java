package com.sistemareserva.service_categoria.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistemareserva.service_categoria.controller.dto.CreateCategoryRequest;
import com.sistemareserva.service_categoria.service.CategoryService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService service;

    @PostMapping
    public void createCategory(CreateCategoryRequest request) {
        var category = request.toModel();
        service.createCategory(category);
    }

    @PutMapping("/{id}/quarto/{quartoId}")
    public void addQuartosCategory(@PathVariable("id") Long id, @PathVariable("quartoId") Long quartoId) {
        service.addQuartosCategory(id, quartoId);
    }
    
}
