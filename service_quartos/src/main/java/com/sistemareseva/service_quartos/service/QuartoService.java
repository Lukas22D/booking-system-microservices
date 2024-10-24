package com.sistemareseva.service_quartos.service;

import java.util.List;
import java.util.stream.Collectors;

import java.util.Optional;
import java.util.Collection;
import java.util.Collections;

import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sistemareseva.service_quartos.client.feightClient.ReservasClient;
import com.sistemareseva.service_quartos.client.feightClient.dto.ReservaResponse;
import com.sistemareseva.service_quartos.client.repository.QuartoRepository;
import com.sistemareseva.service_quartos.model.Quartos;

import feign.FeignException;

import org.slf4j.Logger;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class QuartoService {

    private static final Logger logger = LoggerFactory.getLogger(QuartoService.class);

    private final QuartoRepository repository;
    private final CategoryService categoryService;
    private final ReservasClient reservasClient;

    public Quartos createQuarto(Quartos quarto, Long categoryId) {
        quarto.setCategory(categoryService.getCategoryById(categoryId));
        return repository.save(quarto);
    }

    public Quartos getQuartoById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Quarto not found"));
    }

    public List<Quartos> getQuartosByCategory(Long categoryId) {
        return repository.findByCategoryId(categoryId).orElseThrow(() -> new RuntimeException("Quartos not found"));
    }

    public List<Quartos> getQuartosDisponiveis(String dataEntrada, String dataSaida) {
        // Obter os quartos reservados para o período
        List<Long> quartosReservados = Optional
                .ofNullable(reservasClient.findQuartosReservados(dataEntrada, dataSaida).getBody())
                .orElse(Collections.emptyList());

        // Se não houver quartos reservados, retornar todos os quartos
        if (quartosReservados.isEmpty()) {
            return repository.findAll();
        }

        // Retornar apenas os quartos que não estão reservados
        return repository.findAll().stream().filter(quarto -> !quartosReservados.contains(quarto.getId()))
                .collect(Collectors.toList());
    }

    public Collection<Quartos> getQuartosDisponiveisByCategory(String dataEntrada, String dataSaida, Long categoriaId) {
        // Obter os quartos reservados para o período
        List<Long> quartosReservados = Optional
                .ofNullable(reservasClient.findQuartosReservados(dataEntrada, dataSaida).getBody())
                .orElse(Collections.emptyList());

        // Se não houver quartos reservados, retornar todos os quartos da categoria
        if (quartosReservados.isEmpty()) {
            return repository.findByCategoryId(categoriaId).orElse(Collections.emptyList());
        }

        // Retornar apenas os quartos que não estão reservados
        return repository.findByCategoryId(categoriaId).orElse(Collections.emptyList()).stream()
                .filter(quarto -> !quartosReservados.contains(quarto.getId())).collect(Collectors.toList());

    }

    public Quartos updateQuarto(Long id, Quartos model, Long categoriaId) {
        return repository.findById(id).map(q -> {
            q.setName(model.getName());
            q.setCategory(categoryService.getCategoryById(categoriaId));
            q.setDescription(model.getDescription());
            q.setPriceNight(model.getPriceNight());
            logger.info("Quarto atualizado com sucesso." + q.getId());
            return repository.save(q);
        }).orElseThrow(() -> new RuntimeException("Quarto not found"));
    }

    public void deleteQuarto(Long id) {
    // Verifica se o quarto está reservado através do client
    try {
        ResponseEntity<ReservaResponse> response = reservasClient.findByQuartoId(id);
        
        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            throw new RuntimeException("O quarto já está reservado.");
        }
    } catch (FeignException.NotFound e) {
        // Trata o erro 404 (quarto não reservado)
        logger.info("Nenhuma reserva encontrada para o quarto " + id + ". Continuando com a exclusão...");
    }

    // Verifica se o quarto existe no repositório e o deleta
    repository.findById(id).ifPresentOrElse(
        quarto -> {
            repository.delete(quarto);
            logger.info("Quarto deletado com sucesso.");
        },
        () -> {
            throw new RuntimeException("Quarto não encontrado.");
        }
    );
}

}
