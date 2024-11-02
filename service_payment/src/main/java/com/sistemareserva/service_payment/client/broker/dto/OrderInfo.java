package com.sistemareserva.service_payment.client.broker.dto;

import java.util.List;

import com.sistemareserva.service_payment.model.Transaction;

public record OrderInfo (
    List<Long> idReserva,
    String Status
) {
 

    public static OrderInfo fromTransaction(List<Transaction> transaction, String status) {
        return new OrderInfo(
            transaction.stream()
                .map(Transaction::getIdReserva)
                .toList(),
                status
        );
    }
}
