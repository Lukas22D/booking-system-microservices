package com.sistemareserva.service_payment.infra.adapters.client;

import org.springframework.stereotype.Service;

import com.sistemareserva.service_payment.domain.entity.OrderEntity;
import com.sistemareserva.service_payment.infra.adapters.client.feignClient.ReservasClient;
import com.sistemareserva.service_payment.infra.gateways.ReservasGateway;
import com.sistemareserva.service_payment.infra.mapper.ReservasMapper;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ReservasAdapter implements ReservasGateway{

    private final ReservasClient reservasClient;

    @Override
    public List<OrderEntity> getReserva(Long idHospede) {
        var response = reservasClient.findByIdHospedeAndStatusNull(idHospede);
        if (response.getBody() == null) {
            return null;
        }
        var body = response.getBody();
        if (body == null) {
            return null;
        }
        return body.stream().map(ReservasMapper::toEntity).collect(Collectors.toList());
    }
    
}
