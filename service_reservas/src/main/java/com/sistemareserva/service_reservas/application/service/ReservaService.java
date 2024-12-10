
package com.sistemareserva.service_reservas.application.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.sistemareserva.service_reservas.infra.adapters.client.feignClient.QuartosClient;
import com.sistemareserva.service_reservas.infra.adapters.client.feignClient.dto.QuartosReponse;
import com.sistemareserva.service_reservas.infra.adapters.persistence.repository.model.StatusReserva;
import com.sistemareserva.service_reservas.infra.gateways.ReservaRepositoryGateway;
import com.sistemareserva.service_reservas.application.domain.ReservaEntity;

import java.sql.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;


@Service
public class ReservaService {
    
    private final ReservaRepositoryGateway repository;
    private final QuartosClient quartosClient;

    private final static Logger logger = LoggerFactory.getLogger(ReservaService.class);

    public ReservaService(ReservaRepositoryGateway repository, QuartosClient quartosClient) {
        this.repository = repository;
        this.quartosClient = quartosClient;
    }


    @Async
    public CompletableFuture<ReservaEntity> saveReserva(Long idQuarto, Long idHospede, Date dataEntrada, Date dataSaida) {
        CompletableFuture<QuartosReponse> quarto = CompletableFuture.completedFuture(quartosClient.getQuartoById(idQuarto)).thenApply(ResponseEntity::getBody);
        
        CompletableFuture<ReservaEntity> reserva = quarto.thenApply(q -> {
            repository.existsReserva(q.id(), dataEntrada, dataSaida).ifPresent(exists -> {
                if (exists) {
                    logger.error("Quarto já reservado");
                    throw new RuntimeException("Quarto já reservado");
                }
            });
            logger.info("Quarto encontrado");
            return new ReservaEntity(null,idQuarto, idHospede, dataEntrada, dataSaida, q.preco());
        });
                
        return reserva.thenApply(repository::save);
    }

    public List<ReservaEntity> findByIdHospedeAndStatusNull (Long idHospede) {
        return repository.findByIdHospedeAndStatusIsNull(idHospede).orElse(null);
    }

    public List<ReservaEntity> findByIdHospedeAndStatus(Long idHospede, String status) {
        StatusReserva statusReserva = StatusReserva.valueOf(status);
        return repository.findByIdHospedeAndStatus(idHospede, statusReserva).orElse(null);
    }
    

    public Boolean existsReserva(Long idQuarto, Date dataEntrada, Date dataSaida) {
        return repository.existsReserva(idQuarto, dataEntrada, dataSaida).orElse(false);
    }

    public List<Long> findQuartosReservados(Date dataEntrada, Date dataSaida) {
        return repository.findQuartosReservados(dataEntrada, dataSaida);
    }

    public ReservaEntity findReservaById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    public ReservaEntity findByQuartoId(Long idQuarto) {
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
    public  CompletableFuture<List<ReservaEntity>> findByIdHospede(Long idHospede) {
        return  CompletableFuture.completedFuture(repository.findByIdHospede(idHospede));
    }

    @Async
    public void UpdateStatusReserva(List<Long> idReserva, String status) {
        repository.findByIdIn(idReserva).forEach(reserva -> {
            reserva.UpdateStatusReserva(status);
            repository.save(reserva);
        });
    }

}
