package com.sistemareserva.service_payment.infra.gateways;

import java.util.concurrent.CompletableFuture;

import com.sistemareserva.service_payment.infra.adapters.paypal.dto.OrderRequest;
import com.sistemareserva.service_payment.infra.adapters.paypal.dto.OrderResponse;
import com.sistemareserva.service_payment.infra.adapters.paypal.dto.ReceiveOrderUpdate;

public interface PaymentGateway {

    CompletableFuture<OrderResponse> createOrder(OrderRequest orderRequest);

    public ReceiveOrderUpdate OrderRecive (String order);
}
