package com.sistemareseva.service_quartos.service;

import org.springframework.stereotype.Service;

import com.sistemareseva.service_quartos.client.repository.CategoryRepository;
import com.sistemareseva.service_quartos.model.Category;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;


    public void createCategory(Category category) {
        categoryRepository.save(category);
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    


    
}
