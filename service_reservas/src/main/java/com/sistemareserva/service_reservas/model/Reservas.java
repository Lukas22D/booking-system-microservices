package com.sistemareserva.service_reservas.model;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.GenerationType;

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

    private Boolean Pagamento;
    
    public Reservas(Long idQuarto, Long idHospede, Date dataEntrada, Date dataSaida) {
        this.idQuarto = idQuarto;
        this.idHospede = idHospede;
        this.dataEntrada = dataEntrada;
        this.dataSaida = dataSaida;
        this.Pagamento = true;
    }
}
