package com.sistemareserva.service_reservas.infra.gateways;

import com.sistemareserva.service_reservas.application.domain.ReservaEntity;
import com.sistemareserva.service_reservas.infra.adapters.persistence.repository.model.StatusReserva;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface ReservaRepositoryGateway {

    ReservaEntity save(ReservaEntity reservaEntity);

    Optional<List<ReservaEntity>> findByIdHospedeAndStatusIsNull(Long idHospede);

    Optional<List<ReservaEntity>> findByIdHospedeAndStatus(Long idHospede, StatusReserva status);

    Optional<Boolean> existsReserva(Long idQuarto, Date dataEntrada, Date dataSaida);

    Optional<ReservaEntity> findById(Long id);

    Optional<ReservaEntity> findByIdQuarto(Long idQuarto);

    List<ReservaEntity> findByIdHospede(Long idHospede);

    List<ReservaEntity> findByIdIn(List<Long> idReserva);

    void delete(ReservaEntity reservaEntity);

    List<Long> findQuartosReservados(Date dataEntrada, Date dataSaida);

}