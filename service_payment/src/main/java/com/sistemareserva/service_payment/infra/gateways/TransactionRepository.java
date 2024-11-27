package com.sistemareserva.service_payment.infra.gateways;

import com.sistemareserva.service_payment.domain.entity.TransactionEntity;
import java.util.List;

public interface TransactionRepository {

    void saveAll(List<TransactionEntity> transactions);

    List<TransactionEntity> findByIdPagamento(String idPagamento);

    void save (TransactionEntity transaction);
    
}
