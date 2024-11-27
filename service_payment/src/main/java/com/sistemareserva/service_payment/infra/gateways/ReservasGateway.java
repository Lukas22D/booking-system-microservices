package com.sistemareserva.service_payment.infra.gateways;

import com.sistemareserva.service_payment.domain.entity.OrderEntity;
import java.util.List;

public interface ReservasGateway {
    
    public List<OrderEntity> getReserva(Long idHospede);
}
