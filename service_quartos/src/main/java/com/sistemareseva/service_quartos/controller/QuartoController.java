package com.sistemareseva.service_quartos.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.sistemareseva.service_quartos.controller.dto.CreateQuartoRequest;
import com.sistemareseva.service_quartos.controller.dto.QuartosReponse;
import com.sistemareseva.service_quartos.service.QuartoService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/quarto")
public class QuartoController {

    private final QuartoService quartoService;

    public QuartoController(QuartoService quartoService) {
        this.quartoService = quartoService;
    }

    @PostMapping
    public ResponseEntity<QuartosReponse> createQuarto(@RequestBody CreateQuartoRequest request) {
        var quartoCreated = quartoService.createQuarto(request.toModel(), request.categoriaId());
        return ResponseEntity.ok(new QuartosReponse(quartoCreated));
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuartosReponse> updateQuarto(@PathVariable("id") Long id, @RequestBody CreateQuartoRequest request) {
        return ResponseEntity.ok(new QuartosReponse(
                quartoService.updateQuarto(id, request.toModel(), request.categoriaId())));
    }

    @GetMapping("/Categoria/{id}")
    public ResponseEntity<List<QuartosReponse>> getQuartosByCategory(@PathVariable("id") Long id) {
        List<QuartosReponse> response = quartoService.getQuartosByCategory(id).stream().map(QuartosReponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/QuartosDisponiveis/{dataEntrada}/{dataSaida}")
    public ResponseEntity<List<QuartosReponse>> getQuartosDisponiveis(@PathVariable("dataEntrada") LocalDate dataEntrada,
                                                                      @PathVariable("dataSaida") LocalDate dataSaida) {
        List<QuartosReponse> response = quartoService.getQuartosDisponiveis(dataEntrada.toString(), dataSaida.toString()).stream()
                .map(QuartosReponse::new).collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/QuartosDisponiveis/{dataEntrada}/{dataSaida}/{categoriaId}")
    public ResponseEntity<List<QuartosReponse>> getQuartosDisponiveisByCategory(@PathVariable("dataEntrada") LocalDate dataEntrada,
                                                                                @PathVariable("dataSaida") LocalDate dataSaida,
                                                                                @PathVariable("categoriaId") Long categoriaId) {
        List<QuartosReponse> response = quartoService
                .getQuartosDisponiveisByCategory(dataEntrada.toString(), dataSaida.toString(), categoriaId).stream().map(QuartosReponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuarto(@PathVariable("id") Long id) {
        quartoService.deleteQuarto(id);
        return ResponseEntity.noContent().build();
    }
}
