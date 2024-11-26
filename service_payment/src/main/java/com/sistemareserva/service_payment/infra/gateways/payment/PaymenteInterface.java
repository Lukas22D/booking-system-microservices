package com.sistemareserva.service_payment.infra.gateways.payment;

import java.util.concurrent.CompletableFuture;

import com.sistemareserva.service_payment.infra.gateways.payment.paypal.dto.OrderRequest;
import com.sistemareserva.service_payment.infra.gateways.payment.paypal.dto.OrderResponse;

public interface PaymenteInterface {

    CompletableFuture<OrderResponse> createOrder(OrderRequest orderRequest);
}