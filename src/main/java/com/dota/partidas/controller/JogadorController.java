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

import com.dota.partidas.model.Jogador;
import com.dota.partidas.service.JogadorService;

@RestController
@RequestMapping("/jogadores")
public class JogadorController {

    @Autowired
    private JogadorService jogadorService;

    @GetMapping
    public List<Jogador> listar() {
        return jogadorService.listarTodos();
    }

    @GetMapping("/{id}")
    public Jogador buscarPorId(@PathVariable Long id) {
        return jogadorService.buscarPorId(id);
    }

    @PostMapping
    public Jogador salvar(@RequestBody Jogador jogador) {
        return jogadorService.salvar(jogador);
    }

    @PutMapping("/{id}")
    public Jogador atualizar(@PathVariable Long id, @RequestBody Jogador jogador) {
        Jogador existente = jogadorService.buscarPorId(id);
        existente.setNickname(jogador.getNickname());
        existente.setMmr(jogador.getMmr());
        existente.setHeroisMaisJogados(jogador.getHeroisMaisJogados());
        return jogadorService.salvar(existente);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        jogadorService.excluirPorId(id);
    }
}


