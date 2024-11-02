package com.sistemareserva.service_reservas.client.broker;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sistemareserva.service_reservas.client.broker.dto.OrderInfo;
import com.sistemareserva.service_reservas.service.ReservaService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ConsumerOrder {

    private final ObjectMapper mapper;
    private final ReservaService service;


    @RabbitListener(queues = "payment-queue")
    public void consumer(String message) throws Exception {

        OrderInfo orderInfo = mapper.readValue(message, OrderInfo.class);
        service.UpdateStatusReserva(orderInfo.idReserva(), orderInfo.Status());
        
    }
}
