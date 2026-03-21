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

import com.dota.partidas.dto.JogadorPartidaDTO;
import com.dota.partidas.model.JogadorPartida;
import com.dota.partidas.model.compositkeys.JogadorPartidaId;
import com.dota.partidas.service.JogadorPartidaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/jogador-partida")
public class JogadorPartidaController {

    @Autowired
    private JogadorPartidaService jogadorPartidaService;

    /**
     * Lista todos os registros de desempenho de jogadores em partidas. Retorna
     * a base completa de estatísticas individuais.
     */
    @GetMapping
    public ResponseEntity<List<JogadorPartida>> listar() {
        return ResponseEntity.ok(jogadorPartidaService.listarTodos());
    }

    /**
     * Busca um registro específico usando a chave composta. Como a
     * identificação depende de dois IDs (Partida + Jogador), a URL reflete essa
     * hierarquia.
     */
    @GetMapping("/{idPartida}/{idJogador}")
    public ResponseEntity<JogadorPartida> buscarPorId(@Valid @PathVariable Long idPartida,
            @PathVariable Long idJogador) {

        // Instanciação da chave composta para consulta no Service
        JogadorPartidaId id = new JogadorPartidaId(idPartida, idJogador);
        JogadorPartida jp = jogadorPartidaService.buscarPorId(id);

        if (jp == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(jp);
    }

    /**
     * Vincula um jogador a uma partida e salva suas estatísticas (KDA,
     * Networth). Retorna 201 (Created) em caso de sucesso.
     */
    @PostMapping
    public ResponseEntity<JogadorPartida> salvar(@Valid @RequestBody JogadorPartidaDTO jogadorPartida) {
        JogadorPartida salvo = jogadorPartidaService.salvar(jogadorPartida);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    /**
     * Atualiza as estatísticas de um jogador em uma partida específica. O ID
     * composto garante que estamos alterando o registro exato daquele
     * confronto.
     */
    @PutMapping("/{idPartida}/{idJogador}")
    public ResponseEntity<JogadorPartida> atualizar(
            @PathVariable Long idPartida,
            @PathVariable Long idJogador,
            @RequestBody JogadorPartidaDTO jogadorPartida) {

        try {
            // Recria a chave composta a partir dos parâmetros da URL
            JogadorPartidaId id = new JogadorPartidaId(idPartida, idJogador);
            JogadorPartida atualizado = jogadorPartidaService.atualizar(id, jogadorPartida);
            return ResponseEntity.ok(atualizado);
        } catch (RuntimeException e) {
            // Retorna 404 caso o vínculo entre esse jogador e essa partida não exista
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Remove o registro de participação de um jogador em uma partida.
     */
    @DeleteMapping("/{idPartida}/{idJogador}")
    public ResponseEntity<Void> excluir(@Valid @PathVariable Long idPartida,
            @PathVariable Long idJogador) {

        JogadorPartidaId id = new JogadorPartidaId(idPartida, idJogador);
        JogadorPartida existente = jogadorPartidaService.buscarPorId(id);

        if (existente == null) {
            return ResponseEntity.notFound().build();
        }

        jogadorPartidaService.excluirPorId(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Remove todos os registros de desempenho. Operação útil para limpeza de
     * dados históricos ou resets de temporada.
     */
    @DeleteMapping("/all")
    public ResponseEntity<Void> excluirTudo() {
        jogadorPartidaService.excluirTodos();
        return ResponseEntity.noContent().build();
    }
}
