package com.sistemareserva.service_payment.client.provider.PayPal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sistemareserva.service_payment.client.provider.PayPal.dto.PayPalTokenResponse;

import java.util.Base64;
import java.nio.charset.StandardCharsets;
import java.net.URLEncoder;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PayPalAuthService {

    private static final String PAYPAL_URL = "https://api-m.sandbox.paypal.com/v1/oauth2/token";
    private final RestTemplate restTemplate;

    @Value("${paypal.oauth.client.id}")
    private String clientId;

    @Value("${paypal.oauth.client.secret}")
    private String clientSecret;

    public PayPalAuthService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getAccessToken() throws Exception {
        // Codificando client_id e secret para Base64
        String credentials = clientId + ":" + clientSecret;
        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));

        // Definindo o corpo da requisição como uma string x-www-form-urlencoded
        Map<String, String> bodyParams = Map.of(
                "grant_type", "client_credentials",
                "ignoreCache", "true",
                "return_authn_schemes", "true"
        );

        // Convertendo o HashMap em uma string codificada no formato x-www-form-urlencoded
        String formBody = bodyParams.entrySet().stream()
                .map(entry -> URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8) + "=" + URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8))
                .collect(Collectors.joining("&"));

        // Definindo os cabeçalhos
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "Basic " + encodedCredentials);

        // Criando a entidade da requisição (corpo + cabeçalhos)
        HttpEntity<String> requestEntity = new HttpEntity<>(formBody, headers);

        // Fazendo a requisição POST
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                PAYPAL_URL,
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        // Verificando se a resposta foi bem-sucedida
        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            throw new Exception("Erro na requisição: " + responseEntity.getStatusCode());
        }

        // Processando a resposta
        String jsonResponse = responseEntity.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        PayPalTokenResponse tokenResponse = objectMapper.readValue(jsonResponse, PayPalTokenResponse.class);

        // Retornando o access_token
        return tokenResponse.getAccessToken();
    }
}
