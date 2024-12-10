package com.sistemareserva.service_reservas.infra.adapters.persistence.repository.model;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.GenerationType;
import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
@Entity(name = "tb_reservas")
public class ReservaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long idQuarto;

    private Long idHospede;

    private Date dataEntrada;

    private Date dataSaida;

    private Integer quantidadeDias;

    private BigDecimal unidadeDiaria;

    private BigDecimal valorTotal;

    @Enumerated(EnumType.STRING)
    private StatusReserva status;

    public ReservaModel(Long id, Long idQuarto, Long idHospede, Date dataEntrada, Date dataSaida, BigDecimal unidadeDiaria, StatusReserva status, Integer quantidadeDias, BigDecimal valorTotal) {
        this.id = id;
        this.idQuarto = idQuarto;
        this.idHospede = idHospede;
        this.dataEntrada = dataEntrada;
        this.dataSaida = dataSaida;
        this.status = status;
        this.quantidadeDias = quantidadeDias;
        this.unidadeDiaria = unidadeDiaria;
        this.valorTotal = valorTotal;
    }


}
