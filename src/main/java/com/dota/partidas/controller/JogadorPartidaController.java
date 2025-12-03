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

import com.dota.partidas.model.JogadorPartida;
import com.dota.partidas.model.compositkeys.JogadorPartidaId;
import com.dota.partidas.service.JogadorPartidaService;

@RestController
@RequestMapping("/jogador-partida")
public class JogadorPartidaController {

    @Autowired
    private JogadorPartidaService jogadorPartidaService;

    @GetMapping
    public ResponseEntity<List<JogadorPartida>> listar() {
        return ResponseEntity.ok(jogadorPartidaService.listarTodos());
    }

    @GetMapping("/{idPartida}/{idJogador}")
    public ResponseEntity<JogadorPartida> buscarPorId(@PathVariable Long idPartida,
                                                      @PathVariable Long idJogador) {
        JogadorPartidaId id = new JogadorPartidaId(idPartida, idJogador);
        JogadorPartida jp = jogadorPartidaService.buscarPorId(id);
        if (jp == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(jp);
    }

    @PostMapping
    public ResponseEntity<JogadorPartida> salvar(@RequestBody JogadorPartida jogadorPartida) {
        JogadorPartida salvo = jogadorPartidaService.salvar(jogadorPartida);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @PutMapping("/{idPartida}/{idJogador}")
    public ResponseEntity<JogadorPartida> atualizar(@PathVariable Long idPartida,
                                                    @PathVariable Long idJogador,
                                                    @RequestBody JogadorPartida jogadorPartida) {
        JogadorPartidaId id = new JogadorPartidaId(idPartida, idJogador);
        JogadorPartida existente = jogadorPartidaService.buscarPorId(id);
        if (existente == null) return ResponseEntity.notFound().build();
        existente.setKda(jogadorPartida.getKda());
        return ResponseEntity.ok(jogadorPartidaService.salvar(existente));
    }

    @DeleteMapping("/{idPartida}/{idJogador}")
    public ResponseEntity<Void> excluir(@PathVariable Long idPartida,
                                        @PathVariable Long idJogador) {
        JogadorPartidaId id = new JogadorPartidaId(idPartida, idJogador);
        JogadorPartida existente = jogadorPartidaService.buscarPorId(id);
        if (existente == null) return ResponseEntity.notFound().build();
        jogadorPartidaService.excluirPorId(id);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/all")
    public ResponseEntity<Void> excluirTudo(){
        jogadorPartidaService.excluirTodos();
        return ResponseEntity.noContent().build();
    }
}



