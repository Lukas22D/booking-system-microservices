package com.sistemareserva.service_payment.model;

import java.math.BigDecimal;

import com.sistemareserva.service_payment.model.enums.TransactionStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity(name = "tb_transaction")
public class Transaction {
    
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    private Long idReserva;

    private Integer numDays;

    private Long idPagamento;

    private Long idHospede;

    private BigDecimal valor;

    private TransactionStatus status;

    public Transaction (Long idReserva, Integer numDays, Long idPagamento, Long idHospede, BigDecimal valor) {
        this.idReserva = idReserva;
        this.numDays = numDays;
        this.idPagamento = idPagamento;
        this.idHospede = idHospede;
        this.valor = valor;
        this.status = TransactionStatus.PENDING;
    }
    

}
