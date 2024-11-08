
package com.sistemareserva.service_reservas.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.sistemareserva.service_reservas.client.feignClient.QuartosClient;
import com.sistemareserva.service_reservas.client.feignClient.dto.QuartosReponse;
import com.sistemareserva.service_reservas.client.repository.ReservaRepository;
import com.sistemareserva.service_reservas.model.Reservas;
import com.sistemareserva.service_reservas.model.StatusReserva;

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
            repository.existsReserva(q.id(), dataEntrada, dataSaida).ifPresent(exists -> {
                if (exists) {
                    logger.error("Quarto já reservado");
                    throw new RuntimeException("Quarto já reservado");
                }
            });
            logger.info("Quarto encontrado");
            return new Reservas(idQuarto, idHospede, dataEntrada, dataSaida, q.preco());
        });
                
        return reserva.thenApply(repository::save);
    }

    public List<Reservas> findByIdHospedeAndStatusNull (Long idHospede) {
        return repository.findByIdHospedeAndStatusIsNull(idHospede).orElse(null);
    }

    public List<Reservas> findByIdHospedeAndStatus(Long idHospede, String status) {
        StatusReserva statusReserva = StatusReserva.valueOf(status);
        return repository.findByIdHospedeAndStatus(idHospede, statusReserva).orElse(null);
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


    public void deleteReserva(Long idReserva) {
        repository.findById(idReserva).ifPresentOrElse(
            repository::delete,
            () -> {
                throw new RuntimeException("Reserva não encontrada");
            }
        );
    }

    @Async
    public  CompletableFuture<List<Reservas>> findByIdHospede(Long idHospede) {
        return  CompletableFuture.completedFuture(repository.findByIdHospede(idHospede));
    }

    @Async
    public void UpdateStatusReserva(List<Long> idReserva, String status) {
        repository.findByIdIn(idReserva).forEach(reserva -> {
            switch (status) {
                case "APPROVED":
                    reserva.confirmar();
                    break;
                case "PENDING":
                    reserva.pendente();
                    break;
                case "CANCELADA":
                    reserva.cancelar();
                    break;
                default:
                    throw new RuntimeException("Status inválido");
            }
            repository.save(reserva);
        });
    }

    

}
