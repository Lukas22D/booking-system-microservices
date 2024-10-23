package com.sistemareseva.service_quartos.client.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sistemareseva.service_quartos.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    
}
