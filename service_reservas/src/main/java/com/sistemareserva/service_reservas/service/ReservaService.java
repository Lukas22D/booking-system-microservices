
package com.sistemareserva.service_reservas.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.sistemareserva.service_reservas.client.feightClient.QuartosClient;
import com.sistemareserva.service_reservas.client.feightClient.dto.QuartosReponse;
import com.sistemareserva.service_reservas.client.repository.ReservaRepository;
import com.sistemareserva.service_reservas.model.Reservas;


import java.sql.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;


@Service
public class ReservaService {
    
    private final ReservaRepository repository;
    private final QuartosClient quartosClient;

    private final static Logger logger = LoggerFactory.getLogger(ReservaService.class);

    public ReservaService(ReservaRepository repository, QuartosClient quartosClient) {
        this.repository = repository;
        this.quartosClient = quartosClient;
    }


    @Async
    public CompletableFuture<Reservas> saveReserva(Long idQuarto, Long idHospede, Date dataEntrada, Date dataSaida) {
        CompletableFuture<QuartosReponse> quarto = CompletableFuture.completedFuture(quartosClient.getQuartoById(idQuarto)).thenApply(ResponseEntity::getBody);
        
        CompletableFuture<Reservas> reserva = quarto.thenApply(q -> {
            repository.findByIdQuartoAndStatusConfirmado(q.id()).ifPresent(reservaExistente -> {
                logger.error("Quarto já reservado");
                throw new RuntimeException("Quarto já reservado");
            });
            logger.info("Quarto encontrado");
            return new Reservas(idQuarto, idHospede, dataEntrada, dataSaida, q.preco());
        });
                
        return reserva.thenApply(repository::save);
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

    @Async
    public  CompletableFuture<List<Reservas>> findByIdHospede(Long idHospede) {
        return  CompletableFuture.completedFuture(repository.findByIdHospede(idHospede));
    }


}
