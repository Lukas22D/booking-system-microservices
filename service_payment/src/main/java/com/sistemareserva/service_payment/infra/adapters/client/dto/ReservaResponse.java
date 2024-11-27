package com.sistemareserva.service_payment.infra.adapters.client.dto;

public record ReservaResponse(
    Long id,
    Long idQuarto,
    Long idHospede,
    String dataEntrada,
    String dataSaida,
    String valorTotal,
    String quantidadeDias,
    String UnidadeDiaria,
    String status
){
};
