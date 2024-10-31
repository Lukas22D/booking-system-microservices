package com.sistemareserva.service_payment.client.provider.PayPal;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sistemareserva.service_payment.client.provider.PayPal.dto.OrderRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.AllArgsConstructor;

import java.util.concurrent.CompletableFuture;

@AllArgsConstructor
@Service
public class PayPalService {

    private static final String PAYPAL_API_URL = "https://api-m.sandbox.paypal.com/v2/checkout/orders";
    private final Logger logger = LoggerFactory.getLogger(PayPalService.class);
    private final PayPalAuthService payPalAuthService;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Async
    public CompletableFuture<String> createOrder(OrderRequest orderRequest) throws Exception {

        CompletableFuture<HttpHeaders> headers = CompletableFuture.completedFuture(new HttpHeaders())
                .thenApply( h -> {
                    try{
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

            // Processando a resposta para extrair o link desejado
            CompletableFuture<JsonNode> jsonNode = response.thenApply(r -> {
                try{
                    return objectMapper.readTree(r.getBody());
                } catch (JsonProcessingException e) {
                    logger.error("Erro ao processar resposta: " + e.getMessage());
                    throw new RuntimeException(e);
                }
            });
            CompletableFuture<JsonNode> linksNode = jsonNode.thenApply(j -> {
                return j.get("links");
            });
            return linksNode.thenApply( l ->{
                for (JsonNode link : l) {
                    if ("approve".equals(link.get("rel").asText())) {
                        logger.info("Link de aprovação: " + link.get("href").asText());
                        return link.get("href").asText(); // Retorna o link "approve"
                    }
                }
                return null;
            });
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erro ao criar pedido: " + e.getMessage());
        }  
        return null;
    }
}
