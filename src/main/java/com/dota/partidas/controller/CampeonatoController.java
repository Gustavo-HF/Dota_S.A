package com.dota.partidas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dota.partidas.dto.CampeonatoDTO;
import com.dota.partidas.model.Campeonato;
import com.dota.partidas.service.CampeonatoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/campeonatos")
public class CampeonatoController {

    @Autowired
    private CampeonatoService campeonatoService;

    @GetMapping
    public ResponseEntity<List<Campeonato>> listar() {
        return ResponseEntity.ok(campeonatoService.listarCampeonato());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Campeonato> buscarPorId(@Valid @PathVariable Long id) {
        Campeonato campeonato = campeonatoService.buscarPorId(id);
        if (campeonato == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(campeonato);
    }

    @PostMapping
    public ResponseEntity<Campeonato> salvar(@Valid @RequestBody CampeonatoDTO campeonatoDTO) {
        Campeonato salvo = campeonatoService.salvarCampeonato(campeonatoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Campeonato> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody CampeonatoDTO campeonato) {

        try {
            Campeonato atualizado = campeonatoService.atualizar(id, campeonato);
            return ResponseEntity.ok(atualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirPorId(@Valid @PathVariable Long id) {
        Campeonato existente = campeonatoService.buscarPorId(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        campeonatoService.deletarCampeonatoPorId(id);
        return ResponseEntity.noContent().build();

    }

    @DeleteMapping("/all")
    public ResponseEntity<Void> excluir() {
        campeonatoService.deletarCampeonato();
        return ResponseEntity.noContent().build();

    }

}
