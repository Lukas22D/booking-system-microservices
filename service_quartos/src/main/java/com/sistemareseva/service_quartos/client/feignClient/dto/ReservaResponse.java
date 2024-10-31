package com.sistemareseva.service_quartos.client.feignClient.dto;


public record ReservaResponse(
    Long id,
    Long idQuarto,
    Long idHospede,
    String dataEntrada,
    String dataSaida,
    String status
){};
