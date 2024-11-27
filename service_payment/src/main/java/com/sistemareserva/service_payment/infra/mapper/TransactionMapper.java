package com.sistemareserva.service_payment.infra.mapper;

import com.sistemareserva.service_payment.domain.entity.TransactionEntity;
import com.sistemareserva.service_payment.infra.adapters.persistence.db.model.Transaction;

public class TransactionMapper {
    public static TransactionEntity toEntity(Transaction transaction) {
        return new TransactionEntity(transaction.getId(), transaction.getIdReserva(), transaction.getNumDays(), transaction.getIdPagamento(), transaction.getIdHospede(), transaction.getValor());
    }

    public static Transaction toModel(TransactionEntity transactionEntity) {
        return new Transaction(transactionEntity.getId(),transactionEntity.getIdReserva(), transactionEntity.getNumDays(), transactionEntity.getIdPagamento(), transactionEntity.getIdHospede(), transactionEntity.getValor());
    }
}
