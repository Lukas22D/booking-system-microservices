package com.sistemareserva.service_payment.infra.gateways;

import java.util.concurrent.CompletableFuture;

import com.sistemareserva.service_payment.domain.entity.OrderEntity;
import com.sistemareserva.service_payment.infra.adapters.paypal.dto.ReceiveOrderUpdate;

import java.util.List;

public interface PaymentGateway {

    CompletableFuture<List<OrderEntity>> createOrder(List<OrderEntity> order);

    public ReceiveOrderUpdate OrderRecive (String order);
}
