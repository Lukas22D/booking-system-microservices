package com.sistemareserva.service_payment.infra.adapters.messageBroker.dto;

import java.util.List;

import com.sistemareserva.service_payment.domain.entity.TransactionEntity;

public record OrderInfo (
    List<Long> idReserva,
    Long idHospede,
    String Status
) {
 

    public static OrderInfo fromTransaction(List<TransactionEntity> transaction, String status) {
        return new OrderInfo(
            transaction.stream()
                .map(TransactionEntity::getIdReserva)
                .toList(),
            transaction.get(0).getIdHospede(),
                status
        );
    }
}
