package com.sistemareserva.service_payment.infra.adapters.persistence.db.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistemareserva.service_payment.infra.adapters.persistence.db.model.Transaction;

import java.util.List;

@Repository
public interface TransactionJPARepository extends JpaRepository<Transaction, Long>{

    List<Transaction> findByIdPagamento(String id);

}