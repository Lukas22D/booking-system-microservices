package com.sistemareserva.service_payment.infra.adapters.paypal;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.sistemareserva.service_payment.infra.adapters.paypal.dto.ReceiveOrderUpdate;
import com.sistemareserva.service_payment.domain.entity.OrderEntity;
import com.sistemareserva.service_payment.infra.adapters.paypal.dto.OrderRequest;
import com.sistemareserva.service_payment.infra.gateways.PaymentGateway;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import java.util.concurrent.CompletableFuture;

@AllArgsConstructor
@Service
public class PayPalAdapter implements PaymentGateway {

    private static final String PAYPAL_API_URL = "https://api-m.sandbox.paypal.com/v2/checkout/orders";
    private final Logger logger = LoggerFactory.getLogger(PayPalAdapter.class);
    private final PayPalAuthService payPalAuthService;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Override
    @Async
    public CompletableFuture<List<OrderEntity>> createOrder(List<OrderEntity> order) {

        CompletableFuture<HttpHeaders> headers = CompletableFuture.completedFuture(new HttpHeaders())
                .thenApply(h -> {
                    try {
                        logger.info("Token de acesso: " + payPalAuthService.getAccessToken());
                        h.set("Content-Type", "application/json");
                        h.set("Authorization", "Bearer " + payPalAuthService.getAccessToken());
                        h.set("Prefer", "return=representation");
                        return h;
                    } catch (Exception e) {
                        logger.error("Erro ao criar cabeçalho: " + e.getMessage());
                        throw new RuntimeException(e);
                    }
                });

        CompletableFuture<String> requestBody = CompletableFuture.completedFuture(objectMapper)
                .thenApply(om -> {
                    OrderRequest orderRequest = new OrderRequest(order);
                    logger.info("Pedido JSON: " + orderRequest.toString());
                    try {
                        return om.writeValueAsString(orderRequest);
                    } catch (JsonProcessingException e) {
                        logger.error("Erro ao criar pedido JSON: " + e.getMessage());
                        throw new RuntimeException(e);
                    }
                });

        try {

            CompletableFuture<HttpEntity<String>> entity = headers.thenCombine(requestBody, (h, b) -> {
                return new HttpEntity<>(b, h);
            });

            // Enviando a requisição HTTP POST para criar o pedido
            CompletableFuture<ResponseEntity<String>> response = entity.thenApply(e -> {
                return restTemplate.exchange(PAYPAL_API_URL, HttpMethod.POST, e, String.class);
            });
            logger.info("Pedido criado com sucesso: " + response);

            return response.thenApply(r -> {
                try {
                    JsonNode jsonResponse = objectMapper.readTree(r.getBody());
                    String orderId = jsonResponse.get("id").asText();
                    String approveLink = StreamSupport.stream(jsonResponse.get("links").spliterator(), false)
                            .filter(link -> "approve".equals(link.get("rel").asText()))
                            .map(link -> link.get("href").asText())
                            .findFirst()
                            .orElse(null);

                    logger.info("ID do pedido: " + orderId);
                    logger.info("Link de aprovação: " + approveLink);
                    
                    if(approveLink == null) {
                        throw new RuntimeException("Link de aprovação não encontrado");
                    }
                    return order.stream().map(o -> {
                        o.setIdOrder(orderId);
                        o.setLinkOrder(approveLink);
                        return o;
                    }).collect(Collectors.toList());

                } catch (JsonProcessingException e) {
                    logger.error("Erro ao processar resposta: " + e.getMessage());
                    throw new RuntimeException(e);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erro ao criar pedido: " + e.getMessage());
            return CompletableFuture.failedFuture(e);
        }
    }

    @Override
    public ReceiveOrderUpdate OrderRecive(String order) {
        try {
            // Cria o ObjectMapper para ler o JSON
            JsonNode root = objectMapper.readTree(order);

            // Extrai "status" e "id" da resposta JSON
            String status = root.path("resource").path("status").asText();
            String id = root.path("resource").path("id").asText();

            return new ReceiveOrderUpdate(status, id);
        } catch (JsonProcessingException e) {
            logger.error("Erro ao processar resposta: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
