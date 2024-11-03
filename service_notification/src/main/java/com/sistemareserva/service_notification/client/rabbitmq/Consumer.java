package com.sistemareserva.service_notification.client.rabbitmq;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sistemareserva.service_notification.client.feignClient.ReservaClient;
import com.sistemareserva.service_notification.client.feignClient.UserClient;
import com.sistemareserva.service_notification.client.feignClient.dto.UserResponse;
import com.sistemareserva.service_notification.client.rabbitmq.dto.OrderInfo;
import com.sistemareserva.service_notification.model.EmailModel;
import com.sistemareserva.service_notification.client.feignClient.dto.ReservaResponse;
import com.sistemareserva.service_notification.service.EmailService;

import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class Consumer {
    
    private final EmailModel modelos;
    private final ObjectMapper mapper;
    private final EmailService emailService;
    private final ReservaClient reservaClient;
    private final UserClient  userClient;
    private final Logger logger = LoggerFactory.getLogger(Consumer.class);

    @RabbitListener(queues = "payment-queue")
    public CompletableFuture<Void> consumerOrder (String message) throws Exception {
        OrderInfo orderInfo = mapper.readValue(message, OrderInfo.class);

        CompletableFuture<UserResponse> user = CompletableFuture.supplyAsync(() -> {
            return userClient.getUser(orderInfo.idHospede()).getBody();
        });

        CompletableFuture<List<ReservaResponse>> reservas = reservaClient.findByIdHospede(orderInfo.idHospede()).thenApply(response -> {
            return response.getBody();
        });

        return user.thenCombine(reservas, (userResponse, reservasResponse) -> {
            reservasResponse.forEach(reserva -> {
                switch (orderInfo.Status()) {
                    case "APPROVED":
                        break;
                    case "PENDING":
                        break;
                        
                
                    default:
                        break;
                }
            });
            return null;
        });

    }
}
