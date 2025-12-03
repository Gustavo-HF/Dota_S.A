package com.dota.partidas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
    public List<JogadorPartida> listar() {
        return jogadorPartidaService.listarTodos();
    }

    @GetMapping("/{idPartida}/{idJogador}")
    public JogadorPartida buscarPorId(@PathVariable Long idPartida,
                                      @PathVariable Long idJogador) {
        JogadorPartidaId id = new JogadorPartidaId(idPartida, idJogador);
        return jogadorPartidaService.buscarPorId(id);
    }

    @PostMapping
    public JogadorPartida salvar(@RequestBody JogadorPartida jogadorPartida) {
        return jogadorPartidaService.salvar(jogadorPartida);
    }

    @PutMapping("/{idPartida}/{idJogador}")
    public JogadorPartida atualizar(@PathVariable Long idPartida,
                                    @PathVariable Long idJogador,
                                    @RequestBody JogadorPartida jogadorPartida) {
        JogadorPartidaId id = new JogadorPartidaId(idPartida, idJogador);
        JogadorPartida existente = jogadorPartidaService.buscarPorId(id);
        existente.setKda(jogadorPartida.getKda());
        return jogadorPartidaService.salvar(existente);
    }

    @DeleteMapping("/{idPartida}/{idJogador}")
    public void excluir(@PathVariable Long idPartida,
                        @PathVariable Long idJogador) {
        JogadorPartidaId id = new JogadorPartidaId(idPartida, idJogador);
        jogadorPartidaService.excluirPorId(id);
    }
}

