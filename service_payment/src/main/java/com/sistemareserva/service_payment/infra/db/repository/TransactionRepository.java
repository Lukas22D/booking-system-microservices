package com.sistemareserva.service_payment.infra.db.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistemareserva.service_payment.infra.db.model.Transaction;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>{

    List<Transaction> findByIdPagamento(String id);

}