package com.sistemareseva.service_quartos.controller.dto;

import java.math.BigDecimal;
import java.util.HashMap;

import com.sistemareseva.service_quartos.model.Quartos;

public record QuartosReponse(
    Long id,
    String nome,
    String descricao,
    BigDecimal preco,
    Integer capacidade,
    HashMap<Long, String> categoria
) {
        
        public QuartosReponse (Quartos quartos) {
            this(
                quartos.getId(),
                quartos.getName(),
                quartos.getDescription(),
                quartos.getPriceNight(),
                quartos.getCapacity(),
                new HashMap<Long, String>() {{
                    put(quartos.getCategory().getId(), quartos.getCategory().getName());
                }}
            );
        }
    
}
