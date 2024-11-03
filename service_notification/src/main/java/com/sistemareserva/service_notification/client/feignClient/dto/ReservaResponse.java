package com.sistemareserva.service_notification.client.feignClient.dto;

public record ReservaResponse(
    Long id,
    Long idQuarto,
    Long idHospede,
    String dataEntrada,
    String dataSaida,
    String valorTotal,
    String quantidadeDias,
    String UnidadeDiaria
) {
    
}
