package com.sistemareserva.service_reservas.model;

import java.sql.Date;
import java.time.temporal.ChronoUnit;

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
public class Reservas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long idQuarto;

    private Long idHospede;

    private Date dataEntrada;

    private Date dataSaida;

    private Integer quantidadeDias;

    private BigDecimal UnidadeDiaria;

    private BigDecimal valorTotal;

    @Enumerated(EnumType.STRING)
    private StatusReserva status;

    public Reservas(Long idQuarto, Long idHospede, Date dataEntrada, Date dataSaida, BigDecimal UnidadeDiaria) {
        this.idQuarto = idQuarto;
        this.idHospede = idHospede;
        this.dataEntrada = dataEntrada;
        this.dataSaida = dataSaida;
        this.status = null;
        this.quantidadeDias = (int) ChronoUnit.DAYS.between(
                dataEntrada.toLocalDate(),
                dataSaida.toLocalDate());
        this.UnidadeDiaria = UnidadeDiaria;
        this.valorTotal = UnidadeDiaria.multiply(BigDecimal.valueOf(this.quantidadeDias));
    }

    public void confirmar() {
        this.status = StatusReserva.CONFIRMADA;
    }

    public void cancelar() {
        this.status = StatusReserva.CANCELADA;
    }

    public void pendente() {
        this.status = StatusReserva.PENDENTE;
    }

    public void finalizar() {
        this.status = StatusReserva.FINALIZADA;
    }
}
