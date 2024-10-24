package com.sistemareserva.service_reservas.service;

import org.springframework.stereotype.Service;

import com.sistemareserva.service_reservas.client.repository.ReservaRepository;
import com.sistemareserva.service_reservas.model.Reservas;

import lombok.AllArgsConstructor;

import java.sql.Date;
import java.util.List;

@AllArgsConstructor
@Service
public class ReservaService {
    
    private final ReservaRepository repository;

    public void saveReserva(Long idQuarto, Long idHospede, Date dataEntrada, Date dataSaida) {
        repository.save(new Reservas(idQuarto, idHospede, dataEntrada, dataSaida));
    }

    public Boolean existsReserva(Long idQuarto, Date dataEntrada, Date dataSaida) {
        return repository.existsReserva(idQuarto, dataEntrada, dataSaida).orElse(false);
    }

    public List<Long> findQuartosReservados(Date dataEntrada, Date dataSaida) {
        return repository.findQuartosReservados(dataEntrada, dataSaida);
    }

    public Reservas findReservaById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    public Reservas findByQuartoId(Long idQuarto) {
        return repository.findByIdQuarto(idQuarto).orElse(null);
    }


}
