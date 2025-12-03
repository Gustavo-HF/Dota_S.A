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

import com.dota.partidas.model.Partida;
import com.dota.partidas.service.PartidaService;

@RestController
@RequestMapping("/partidas")
public class PartidaController {

    @Autowired
    private PartidaService partidaService;

    @GetMapping
    public List<Partida> listar() {
        return partidaService.listarTodas();
    }

    @GetMapping("/{id}")
    public Partida buscarPorId(@PathVariable Long id) {
        return partidaService.buscarPorId(id);
    }

    @PostMapping
    public Partida salvar(@RequestBody Partida partida) {
        return partidaService.salvar(partida);
    }

    @PutMapping("/{id}")
    public Partida atualizar(@PathVariable Long id, @RequestBody Partida partida) {
        Partida existente = partidaService.buscarPorId(id);
        existente.setDuracaoPartida(partida.getDuracaoPartida());
        existente.setPicks(partida.getPicks());
        existente.setBans(partida.getBans());
        existente.setMvp(partida.getMvp());
        existente.setTimeA(partida.getTimeA());
        existente.setTimeB(partida.getTimeB());
        existente.setCampeonato(partida.getCampeonato());
        return partidaService.salvar(existente);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        partidaService.excluirPorId(id);
    }
}
