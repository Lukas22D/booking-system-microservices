package com.sistemareserva.service_notification.client.rabbitmq;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sistemareserva.service_notification.client.feignClient.QuartoClient;
import com.sistemareserva.service_notification.client.feignClient.ReservaClient;
import com.sistemareserva.service_notification.client.feignClient.UserClient;
import com.sistemareserva.service_notification.client.feignClient.dto.UserResponse;
import com.sistemareserva.service_notification.client.rabbitmq.dto.OrderInfo;
import com.sistemareserva.service_notification.model.EmailModel;
import com.sistemareserva.service_notification.client.feignClient.dto.QuartosReponse;
import com.sistemareserva.service_notification.client.feignClient.dto.ReservaResponse;
import com.sistemareserva.service_notification.service.EmailService;
import com.sistemareserva.service_notification.client.rabbitmq.dto.EmailDTO;

import java.util.stream.Collectors;
import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class Consumer {
    
    private final EmailModel modelos;
    private final ObjectMapper mapper;
    private final EmailService emailService;
    private final QuartoClient quartoClient;
    private final ReservaClient reservaClient;
    private final UserClient  userClient;
    private final Logger logger = LoggerFactory.getLogger(Consumer.class);


@RabbitListener(queues = "notification-queue")
public void consumerOrder(String message) throws Exception {
    OrderInfo orderInfo = mapper.readValue(message, OrderInfo.class);

    // Fetch reservation details synchronously
    ResponseEntity<List<ReservaResponse>> responseEntity = reservaClient.findByIdHospede(orderInfo.idHospede());
    List<ReservaResponse> reservas = responseEntity.getBody();
    if (reservas == null) {
        logger.error("Received null body from reservaClient");
        return;
    }
    List<EmailDTO> emailDTOs = reservas.stream().map(reserva -> {
        try {
            QuartosReponse quarto = quartoClient.getQuartoById(reserva.idQuarto()).getBody();
            UserResponse user = userClient.getUser(reserva.idHospede()).getBody();
            if (quarto == null) {
                logger.error("Received null body from quartoClient for idQuarto: {}", reserva.idQuarto());
                return null;
            }
            if (user == null) {
                logger.error("Received null body from userClient for idHospede: {}", reserva.idHospede());
                return null;
            }
            return new EmailDTO(user.email(), user.email(), reserva.dataEntrada(), reserva.dataSaida(),
                                quarto.nome(), reserva.id().toString(), reserva.valorTotal(), orderInfo.Status());
        } catch (Exception e) {
            logger.error("Error to get data from client", e);
            return null;
        }
    }).collect(Collectors.toList());

    // Process each email asynchronously
    CompletableFuture.runAsync(() -> emailDTOs.forEach(email -> {
        try {
            logger.info("Processing email for {}", email.emailHospede());
            handleEmailSending(email);
        } catch (Exception e) {
            logger.error("Error sending email", e);
        }
    }));
}

private void handleEmailSending(EmailDTO email) throws Exception {
    switch (email.status()) {
        case "APPROVED":
            logger.info("Sending email to {}", email.emailHospede());
            String subjectReserva = modelos.buildReservationConfirmationEmail(
                    email.nomeHospede(), email.dataCheckin(), email.dataCheckout(),
                    email.TipoQuarto(), email.numeroReserva(), email.valorTotal(), "APROVADA");

            String subjectPagamento = modelos.buildPaymentConfirmedEmail(
                    email.nomeHospede(), email.dataCheckin(), email.dataCheckout(),
                    email.TipoQuarto(), email.numeroReserva(), email.valorTotal());

            emailService.sendHtmlEmail(email.emailHospede(), "RESERVA CONFIRMADA", subjectReserva);
            emailService.sendHtmlEmail(email.emailHospede(), "PAGAMENTO CONFIRMADO", subjectPagamento);
            break;
        case "PENDING":
            logger.info("Sending email to {}", email.emailHospede());
            String subjectReservaPendente = modelos.buildPendingReservationEmail(
                    email.nomeHospede(), email.dataCheckin(), email.dataCheckout(),
                    email.TipoQuarto(), email.numeroReserva(), email.valorTotal(), "PENDENTE");
            emailService.sendHtmlEmail(email.emailHospede(), "RESERVA PENDENTE", subjectReservaPendente);
            break;
        default:
            break;
    }
}

}
