package com.sistemareserva.service_reservas.infra.adapters.persistence.repository;

import java.sql.Date;
import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sistemareserva.service_reservas.infra.adapters.persistence.repository.model.ReservaModel;
import com.sistemareserva.service_reservas.infra.adapters.persistence.repository.model.StatusReserva;

@Repository
public interface ReservaRepository extends JpaRepository<ReservaModel, Long> {

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN TRUE ELSE FALSE END FROM tb_reservas r WHERE r.idQuarto = :idQuarto AND r.dataEntrada = :dataEntrada AND r.dataSaida = :dataSaida AND r.status IN (com.sistemareserva.service_reservas.infra.adapters.persistence.repository.model.StatusReserva.PENDENTE, com.sistemareserva.service_reservas.infra.adapters.persistence.repository.model.StatusReserva.CONFIRMADA)")
    Optional<Boolean> existsReserva(@Param("idQuarto") Long idQuarto, @Param("dataEntrada") Date dataEntrada,
            @Param("dataSaida") Date dataSaida);

    @Query("SELECT r.idQuarto FROM tb_reservas r WHERE r.dataEntrada <= :dataSaida AND r.dataSaida >= :dataEntrada AND r.status IN (com.sistemareserva.service_reservas.infra.adapters.persistence.repository.model.StatusReserva.PENDENTE, com.sistemareserva.service_reservas.infra.adapters.persistence.repository.model.StatusReserva.CONFIRMADA)")
    List<Long> findQuartosReservados(@Param("dataEntrada") Date dataEntrada, @Param("dataSaida") Date dataSaida);

    List<ReservaModel> findByIdHospede(Long idHospede);

    Optional<ReservaModel> findByIdQuarto(Long idQuarto);

    @Query("SELECT r FROM tb_reservas r WHERE r.idHospede = :idHospede AND r.status IS NULL")
    Optional<List<ReservaModel>> findByIdHospedeAndStatusIsNull(@Param("idHospede") Long idHospede);

    Optional<List<ReservaModel>> findByIdHospedeAndStatus(Long idHospede, StatusReserva status);

    @Query("SELECT r FROM tb_reservas r WHERE r.idQuarto = :idQuarto AND r.status =  com.sistemareserva.service_reservas.infra.adapters.persistence.repository.model.StatusReserva.CONFIRMADA")
    Optional<ReservaModel> findByIdQuartoAndStatusConfirmado(
            @Param("idQuarto") Long idQuarto);

    List<ReservaModel> findByIdIn(List<Long> idReserva);

}
