package com.sistemareserva.service_payment.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistemareserva.service_payment.service.TransactionService;

import java.util.concurrent.CompletableFuture;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/paypal")
@CrossOrigin(origins = "*") // Adiciona a configuração de CORS para o controlador
public class PaymentController {

    private final TransactionService transactionService;
    private final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    @GetMapping("/create-order")
    public CompletableFuture<ResponseEntity<String>> createOrder() throws Exception {


        return transactionService.createOrder(1L).thenApply(ResponseEntity::ok);
    }

    @PostMapping("/capture-order")
    public ResponseEntity<String> captureOrder(@RequestBody String orderId) throws Exception {
        // Chama o serviço para capturar o pagamento da ordem
        String captureId = orderId;

        if (captureId != null) {
            // Retorna o ID da captura no corpo da resposta
            logger.info("ID da captura: " + captureId);
            return new ResponseEntity<>(captureId, HttpStatus.OK);
        } else {
            // Se ocorrer algum erro, retornar 500
            return new ResponseEntity<>("Erro ao capturar a ordem", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
