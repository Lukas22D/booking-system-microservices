package com.sistemareseva.service_quartos.service;

import java.util.List;
import java.util.stream.Collectors;

import java.util.Optional;
import java.util.Collection;
import java.util.Collections;
import org.springframework.stereotype.Service;

import com.sistemareseva.service_quartos.client.feightClient.ReservasClient;
import com.sistemareseva.service_quartos.client.repository.QuartoRepository;
import com.sistemareseva.service_quartos.model.Quartos;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class QuartoService {
    
    private final QuartoRepository Quartorepository;
    private final CategoryService categoryService;
    private final ReservasClient reservasClient;

    public Quartos createQuarto(Quartos quarto, Long categoryId) {
        quarto.setCategory(categoryService.getCategoryById(categoryId));
        return Quartorepository.save(quarto);
    }

    public Quartos getQuartoById(Long id) {
        return Quartorepository.findById(id).orElseThrow(() -> new RuntimeException("Quarto not found"));
    }

    public List<Quartos> getQuartosByCategory(Long categoryId) {
        return Quartorepository.findByCategoryId(categoryId).orElseThrow(() -> new RuntimeException("Quartos not found"));
    }

    public List<Quartos> getQuartosDisponiveis(String dataEntrada, String dataSaida) {
        //Obter os quartos reservados para o período
        List<Long> quartosReservados = Optional.ofNullable(reservasClient.findQuartosReservados(dataEntrada, dataSaida).getBody())
                                           .orElse(Collections.emptyList()); 

        //Se não houver quartos reservados, retornar todos os quartos
        if (quartosReservados.isEmpty()) {
            return  Quartorepository.findAll();
        }

        //Retornar apenas os quartos que não estão reservados
        return Quartorepository.findAll().stream().filter(quarto -> !quartosReservados.contains(quarto.getId())).collect(Collectors.toList());                            
    }

    public Collection<Quartos> getQuartosDisponiveisByCategory(String dataEntrada, String dataSaida,Long categoriaId) {
        List<Long> quartosReservados = Optional.ofNullable(reservasClient.findQuartosReservados(dataEntrada, dataSaida).getBody())
                .orElse(Collections.emptyList());

        if (quartosReservados.isEmpty()) {
            return Quartorepository.findByCategoryId(categoriaId).orElse(Collections.emptyList());
        }

        return Quartorepository.findByCategoryId(categoriaId).orElse(Collections.emptyList()).stream().filter(quarto -> !quartosReservados.contains(quarto.getId())).collect(Collectors.toList());

    }


}
