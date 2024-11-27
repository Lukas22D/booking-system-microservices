package com.sistemareserva.service_payment.infra.adapters.persistence;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.sistemareserva.service_payment.domain.entity.TransactionEntity;
import com.sistemareserva.service_payment.infra.adapters.persistence.db.repository.TransactionJPARepository;
import com.sistemareserva.service_payment.infra.gateways.TransactionRepository;
import com.sistemareserva.service_payment.infra.mapper.TransactionMapper;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Repository
public class TransactionPersistence implements TransactionRepository {

    private final TransactionJPARepository repository;

    @Override
    public void saveAll(List<TransactionEntity> transactions) {
        var transactionsModel = transactions.stream().map(TransactionMapper::toModel).collect(Collectors.toList());
        repository.saveAll(transactionsModel);
    }

    @Override
    public List<TransactionEntity> findByIdPagamento(String idPagamento) {
        return repository.findByIdPagamento(idPagamento).stream().map(TransactionMapper::toEntity).collect(Collectors.toList());
    }

    @Override
    public void save(TransactionEntity transaction) {
        repository.save(TransactionMapper.toModel(transaction));
    }

}
