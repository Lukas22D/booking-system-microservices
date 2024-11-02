package com.sistemareserva.service_payment.client.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.sistemareserva.service_payment.model.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>{

    List<Transaction> findByIdPagamento(String id);

}