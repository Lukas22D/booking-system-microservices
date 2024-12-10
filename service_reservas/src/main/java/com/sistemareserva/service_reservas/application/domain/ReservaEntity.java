package com.sistemareserva.service_reservas.application.domain;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.temporal.ChronoUnit;

import com.sistemareserva.service_reservas.infra.adapters.persistence.repository.model.StatusReserva;

public class ReservaEntity {

    private Long id;

    private Long idQuarto;

    private Long idHospede;

    private Date dataEntrada;

    private Date dataSaida;

    private Integer quantidadeDias;

    private BigDecimal unidadeDiaria;
 
    private BigDecimal valorTotal;

    private StatusReserva status;

    public ReservaEntity(Long id, Long idQuarto, Long idHospede, Date dataEntrada, Date dataSaida, BigDecimal unidadeDiaria) {
        this.id = id;
        this.idQuarto = idQuarto;
        this.idHospede = idHospede;
        this.dataEntrada = dataEntrada;
        this.dataSaida = dataSaida;
        this.quantidadeDias = calculeQuantidadesDias(dataEntrada, dataSaida);
        this.unidadeDiaria = unidadeDiaria;
        this.valorTotal = calculeValorTotal();
        this.status = null;
    }

    private BigDecimal calculeValorTotal() {
        return this.unidadeDiaria.multiply(BigDecimal.valueOf(this.quantidadeDias));
    }

    private int calculeQuantidadesDias(Date dataEntrada, Date dataSaida) {
        return (int) ChronoUnit.DAYS.between(
                dataEntrada.toLocalDate(),
                dataSaida.toLocalDate());
    }

    public void UpdateStatusReserva(String status) {
        switch (status) {
            case "CONFIRMADA":
                this.status = StatusReserva.CONFIRMADA;
                break;
            case "PENDENTE":
                this.status = StatusReserva.PENDENTE;
                break;
            case "CANCELADA":
                this.status = StatusReserva.CANCELADA;
                break;
            case "FINALIZADA":
                this.status = StatusReserva.FINALIZADA;
                break;
            default:
                throw new RuntimeException("Status inválido");
        }
    };

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdQuarto() {
        return idQuarto;
    }

    public void setIdQuarto(Long idQuarto) {
        this.idQuarto = idQuarto;
    }

    public Long getIdHospede() {
        return idHospede;
    }

    public void setIdHospede(Long idHospede) {
        this.idHospede = idHospede;
    }

    public Date getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(Date dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public Date getDataSaida() {
        return dataSaida;
    }

    public void setDataSaida(Date dataSaida) {
        this.dataSaida = dataSaida;
    }

    public Integer getQuantidadeDias() {
        return quantidadeDias;
    }

    public void setQuantidadeDias(Integer quantidadeDias) {
        this.quantidadeDias = quantidadeDias;
    }

    public BigDecimal getUnidadeDiaria() {
        return unidadeDiaria;
    }

    public void setUnidadeDiaria(BigDecimal unidadeDiaria) {
        this.unidadeDiaria = unidadeDiaria;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public StatusReserva getStatus() {
        return status;
    }

    public void setStatus(StatusReserva status) {
        this.status = status;
    }

}
