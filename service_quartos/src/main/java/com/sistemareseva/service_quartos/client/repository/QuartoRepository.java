package com.sistemareseva.service_quartos.client.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistemareseva.service_quartos.model.Quartos;

@Repository
public interface QuartoRepository extends JpaRepository<Quartos, Long>{

    Optional<List<Quartos>> findByCategoryId(Long categoryId);
    
    
}
