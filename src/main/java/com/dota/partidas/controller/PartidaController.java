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

import com.dota.partidas.model.Partida;
import com.dota.partidas.service.PartidaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/partidas")
public class PartidaController {

    @Autowired
    private PartidaService partidaService;

    @GetMapping
    public ResponseEntity<List<Partida>> listar() {
        return ResponseEntity.ok(partidaService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Partida> buscarPorId(@Valid @PathVariable Long id) {
        Partida partida = partidaService.buscarPorId(id);
        if (partida == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(partida);
    }

    @PostMapping
    public ResponseEntity<Partida> salvar(@Valid @RequestBody Partida partida) {
        Partida salvo = partidaService.salvar(partida);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Partida> atualizar(@Valid @PathVariable Long id, @RequestBody Partida partida) {
        Partida existente = partidaService.buscarPorId(id);
        if (existente == null) return ResponseEntity.notFound().build();
        existente.setDuracaoPartida(partida.getDuracaoPartida());
        existente.setPicks(partida.getPicks());
        existente.setBans(partida.getBans());
        existente.setMvp(partida.getMvp());
        existente.setTimeA(partida.getTimeA());
        existente.setTimeB(partida.getTimeB());
        existente.setCampeonato(partida.getCampeonato());
        return ResponseEntity.ok(partidaService.salvar(existente));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@Valid @PathVariable Long id) {
        Partida existente = partidaService.buscarPorId(id);
        if (existente == null) return ResponseEntity.notFound().build();
        partidaService.excluirPorId(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/all")
    public ResponseEntity<Void> excluirTudo(){
        partidaService.excluirTodas();
        return ResponseEntity.noContent().build();
    }

}
