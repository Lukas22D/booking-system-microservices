package com.sistemareserva.service_payment.client.provider.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sistemareserva.service_payment.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long>{

    
}