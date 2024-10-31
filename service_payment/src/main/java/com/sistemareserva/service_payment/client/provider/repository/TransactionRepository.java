package com.sistemareserva.service_payment.client.provider.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistemareserva.service_payment.model.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>{

}