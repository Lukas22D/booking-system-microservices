package com.sistemareserva.service_reservas.client.repository;

import java.sql.Date;
import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sistemareserva.service_reservas.model.Reservas;

@Repository
public interface ReservaRepository extends JpaRepository<Reservas, Long> {

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN TRUE ELSE FALSE END FROM tb_reservas r WHERE r.idQuarto = :idQuarto AND r.dataEntrada = :dataEntrada AND r.dataSaida = :dataSaida AND r.status IN (com.sistemareserva.service_reservas.model.StatusReserva.PENDENTE, com.sistemareserva.service_reservas.model.StatusReserva.CONFIRMADA)")
    Optional<Boolean> existsReserva(@Param("idQuarto") Long idQuarto, @Param("dataEntrada") Date dataEntrada,
            @Param("dataSaida") Date dataSaida);

    @Query("SELECT r.idQuarto FROM tb_reservas r WHERE r.dataEntrada <= :dataSaida AND r.dataSaida >= :dataEntrada AND r.status IN (com.sistemareserva.service_reservas.model.StatusReserva.PENDENTE, com.sistemareserva.service_reservas.model.StatusReserva.CONFIRMADA)")
    List<Long> findQuartosReservados(@Param("dataEntrada") Date dataEntrada, @Param("dataSaida") Date dataSaida);

    List<Reservas> findByIdHospede(Long idHospede);

    Optional<Reservas> findByIdQuarto(Long idQuarto);

    @Query("SELECT r FROM tb_reservas r WHERE r.idQuarto = :idQuarto AND r.status =  com.sistemareserva.service_reservas.model.StatusReserva.CONFIRMADA")
    Optional<Reservas> findByIdQuartoAndStatusConfirmado(
            @Param("idQuarto") Long idQuarto);

    List<Reservas> findByIdIn(List<Long> idReserva);

}
