package com.sistemareseva.service_quartos.controller.dto;

import com.sistemareseva.service_quartos.model.Quartos;
import java.math.BigDecimal;

public record CreateQuartoRequest(
    String nome,
    String descricao,
    BigDecimal preco,
    Integer capacidade,
    Long categoriaId
) {


    public Quartos toModel() {
        return new Quartos(nome, descricao, preco, capacidade);
    }
    
}
