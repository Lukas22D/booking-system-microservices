package com.sistemareserva.service_payment.domain.entity;

import java.math.BigDecimal;

import com.sistemareserva.service_payment.infra.adapters.persistence.db.model.enums.TransactionStatus;

public class TransactionEntity {

    private Long id;
    
     private Long idReserva;

    private Integer numDays;

    private String idPagamento;

    private Long idHospede;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private BigDecimal valor;

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    private TransactionStatus status;

    public TransactionEntity(Long id, Long idReserva, Integer numDays, String idPagamento, Long idHospede, BigDecimal valor) {
        this.id = id;
        this.idReserva = idReserva;
        this.numDays = numDays;
        this.idPagamento = idPagamento;
        this.idHospede = idHospede;
        this.valor = valor;
        this.status = TransactionStatus.PENDING;
    }

    public Long getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(Long idReserva) {
        this.idReserva = idReserva;
    }

    public Integer getNumDays() {
        return numDays;
    }

    public void setNumDays(Integer numDays) {
        this.numDays = numDays;
    }

    public String getIdPagamento() {
        return idPagamento;
    }

    public void setIdPagamento(String idPagamento) {
        this.idPagamento = idPagamento;
    }

    public Long getIdHospede() {
        return idHospede;
    }

    public void setIdHospede(Long idHospede) {
        this.idHospede = idHospede;
    }

}
