package com.sistemareseva.service_quartos.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Quartos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private BigDecimal priceNight;
    private Integer capacity;
    private Double rating;

    @ManyToOne
    private Category category;

    public Quartos(String name, String description, BigDecimal priceNight, Integer capacity) {
        this.name = name;
        this.description = description;
        this.priceNight = priceNight;
        this.capacity = capacity;
        this.rating = 0.0;
    }
    
}
