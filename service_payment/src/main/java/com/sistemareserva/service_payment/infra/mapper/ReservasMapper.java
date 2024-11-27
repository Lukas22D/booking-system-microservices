package com.sistemareserva.service_payment.infra.mapper;
import com.sistemareserva.service_payment.domain.entity.OrderEntity;
import com.sistemareserva.service_payment.infra.adapters.client.dto.ReservaResponse;

public class ReservasMapper {
        
        public static OrderEntity toEntity(ReservaResponse reservaResponse) {
            return new OrderEntity(
                reservaResponse.id(),
                reservaResponse.idQuarto(),
                reservaResponse.idHospede(),
                reservaResponse.quantidadeDias(),
                reservaResponse.UnidadeDiaria(),
                reservaResponse.valorTotal()
            );
        };
    
}
