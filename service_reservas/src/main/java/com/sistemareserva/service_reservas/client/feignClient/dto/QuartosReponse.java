package com.sistemareserva.service_reservas.client.feignClient.dto;

import java.math.BigDecimal;
import java.util.HashMap;

public record QuartosReponse(
    Long id,
    String nome,
    String descricao,
    BigDecimal preco,
    Integer capacidade,
    HashMap<Long, String> categoria
){};
