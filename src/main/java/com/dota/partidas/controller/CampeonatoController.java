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
        if (campeonato == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(campeonato);
    }

    @PostMapping
    public ResponseEntity<Campeonato> salvar(@Valid @RequestBody Campeonato campeonato) {
        Campeonato salvo = campeonatoService.salvarCampeonato(campeonato);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Campeonato> atualizar(@Valid @PathVariable Long id, @RequestBody Campeonato campeonato) {
        Campeonato existente = campeonatoService.buscarPorId(id);
        if (existente == null) return ResponseEntity.notFound().build();
        existente.setNome(campeonato.getNome());
        existente.setComecoCamp(campeonato.getComecoCamp());
        existente.setFimCamp(campeonato.getFimCamp());
        existente.setPatchCampeonato(campeonato.getPatchCampeonato());
        existente.setPartidas(campeonato.getPartidas());
        return ResponseEntity.ok(campeonatoService.salvarCampeonato(existente));
    }

    @DeleteMapping("/all")
    public ResponseEntity<Void> excluir(@Valid @PathVariable Long id) {
        Campeonato existente = campeonatoService.buscarPorId(id);
        if (existente == null) return ResponseEntity.notFound().build();
        campeonatoService.deletarCampeonato();
        return ResponseEntity.noContent().build();
    }

}
