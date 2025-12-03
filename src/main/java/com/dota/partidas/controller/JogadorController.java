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

import com.dota.partidas.model.Jogador;
import com.dota.partidas.service.JogadorService;

@RestController
@RequestMapping("/jogadores")
public class JogadorController {

    @Autowired
    private JogadorService jogadorService;

    @GetMapping
    public ResponseEntity<List<Jogador>> listar() {
        return ResponseEntity.ok(jogadorService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Jogador> buscarPorId(@PathVariable Long id) {
        Jogador jogador = jogadorService.buscarPorId(id);
        if (jogador == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(jogador);
    }

    @PostMapping
    public ResponseEntity<Jogador> salvar(@RequestBody Jogador jogador) {
        Jogador salvo = jogadorService.salvar(jogador);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Jogador> atualizar(@PathVariable Long id, @RequestBody Jogador jogador) {
        Jogador existente = jogadorService.buscarPorId(id);
        if (existente == null) return ResponseEntity.notFound().build();
        existente.setNickname(jogador.getNickname());
        existente.setMmr(jogador.getMmr());
        existente.setHeroisMaisJogados(jogador.getHeroisMaisJogados());
        return ResponseEntity.ok(jogadorService.salvar(existente));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        Jogador existente = jogadorService.buscarPorId(id);
        if (existente == null) return ResponseEntity.notFound().build();
        jogadorService.excluirPorId(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/all")
    public ResponseEntity<Void> excluirTudo(){
        jogadorService.excluirTodos();
        return ResponseEntity.noContent().build();

    }
}


