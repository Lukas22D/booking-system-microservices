package com.sistemareserva.service_reservas.infra.adapters.persistence;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.sistemareserva.service_reservas.application.domain.ReservaEntity;
import com.sistemareserva.service_reservas.infra.gateways.ReservaRepositoryGateway;
import com.sistemareserva.service_reservas.infra.mapper.ReservaMapper;
import com.sistemareserva.service_reservas.infra.adapters.persistence.repository.ReservaRepository;
import com.sistemareserva.service_reservas.infra.adapters.persistence.repository.model.StatusReserva;


@Repository
public class ReservaRepositoryAdapter implements ReservaRepositoryGateway {

    private final ReservaRepository repository;

    public ReservaRepositoryAdapter(ReservaRepository repository) {
        this.repository = repository;
    }

    @Override
    public ReservaEntity save(ReservaEntity reservaEntity) {
        return ReservaMapper.toEntity(repository.save(ReservaMapper.toModel(reservaEntity)));
    }

    @Override
    public Optional<List<ReservaEntity>> findByIdHospedeAndStatusIsNull(Long idHospede) {
        return repository.findByIdHospedeAndStatusIsNull(idHospede)
                .map(reservas -> reservas.stream()
                        .map(ReservaMapper::toEntity)
                        .collect(Collectors.toList()));
    }

    @Override
    public Optional<List<ReservaEntity>> findByIdHospedeAndStatus(Long idHospede, StatusReserva status) {
        return repository.findByIdHospedeAndStatus(idHospede, status)
                .map(reservas -> reservas.stream()
                        .map(ReservaMapper::toEntity)
                        .collect(Collectors.toList())
                    );
    }

    @Override
    public Optional<Boolean> existsReserva(Long idQuarto, Date dataEntrada, Date dataSaida) {
        return repository.existsReserva(idQuarto, dataEntrada, dataSaida);
    }

    @Override
    public Optional<ReservaEntity> findById(Long id) {
        return repository.findById(id)
                .map(ReservaMapper::toEntity);
    }

    @Override
    public Optional<ReservaEntity> findByIdQuarto(Long idQuarto) {
        return repository.findByIdQuarto(idQuarto)
                .map(ReservaMapper::toEntity);
    }

    @Override
    public List<ReservaEntity> findByIdHospede(Long idHospede) {
        return repository.findByIdHospede(idHospede).stream()
                .map(ReservaMapper::toEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReservaEntity> findByIdIn(List<Long> idReserva) {
        return repository.findByIdIn(idReserva).stream()
                .map(ReservaMapper::toEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(ReservaEntity reservaEntity) {
        repository.delete(ReservaMapper.toModel(reservaEntity));
    }

    @Override
    public List<Long> findQuartosReservados(Date dataEntrada, Date dataSaida) {
        return repository.findQuartosReservados(dataEntrada, dataSaida);
    }

}
