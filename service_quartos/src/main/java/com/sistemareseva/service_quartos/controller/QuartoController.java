package com.sistemareseva.service_quartos.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistemareseva.service_quartos.controller.dto.CreateQuartoRequest;
import com.sistemareseva.service_quartos.controller.dto.QuartosReponse;
import com.sistemareseva.service_quartos.service.QuartoService;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/quarto")
public class QuartoController {

    private final QuartoService quartoService;

    @PostMapping
    public ResponseEntity<QuartosReponse> createQuarto(CreateQuartoRequest request) {
       var quartoCreated = quartoService.createQuarto(request.toModel(), request.categoriaId());
       return ResponseEntity.ok(new QuartosReponse(quartoCreated));
    }

    @GetMapping("/Categoria/{id}")
    public ResponseEntity<List<QuartosReponse>> getQuartosByCategory(Long id) {
        List<QuartosReponse> response = quartoService.getQuartosByCategory(id).stream().map(QuartosReponse::new).collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/QuartosDisponiveis/{dataEntrada}/{dataSaida}")
    public ResponseEntity<List<QuartosReponse>> getQuartosDisponiveis(String dataEntrada, String dataSaida) {
        List<QuartosReponse> response = quartoService.getQuartosDisponiveis(dataEntrada, dataSaida).stream().map(QuartosReponse::new).collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }
    
}
