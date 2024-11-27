package com.sistemareserva.service_payment.infra.adapters.persistence.db.model;

import java.math.BigDecimal;

import com.sistemareserva.service_payment.infra.adapters.persistence.db.model.enums.TransactionStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

    private String idPagamento;

    private Long idHospede;

    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    public Transaction (Long id, Long idReserva, Integer numDays, String idPagamento, Long idHospede, BigDecimal valor) {
        this.id = id;
        this.idReserva = idReserva;
        this.numDays = numDays;
        this.idPagamento = idPagamento;
        this.idHospede = idHospede;
        this.valor = valor;
        this.status = TransactionStatus.PENDING;
    }
    

}
