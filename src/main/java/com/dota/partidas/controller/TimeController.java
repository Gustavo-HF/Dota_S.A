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

import com.dota.partidas.model.Time;
import com.dota.partidas.service.TimeService;

@RestController
@RequestMapping("/times")
public class TimeController {

    @Autowired
    private TimeService timeService;

    @GetMapping
    public List<Time> listar() {
        return timeService.listarTodos();
    }

    @GetMapping("/{id}")
    public Time buscarPorId(@PathVariable Long id) {
        return timeService.buscarPorId(id);
    }

    @PostMapping
    public Time salvar(@RequestBody Time time) {
        return timeService.salvar(time);
    }

    @PutMapping("/{id}")
    public Time atualizar(@PathVariable Long id, @RequestBody Time time) {
        Time existente = timeService.buscarPorId(id);
        existente.setNome(time.getNome());
        existente.setNumeroJogadores(time.getNumeroJogadores());
        existente.setRegiao(time.getRegiao());
        existente.setClassificacaoMundial(time.getClassificacaoMundial());
        existente.setClassificacaoCampeonato(time.getClassificacaoCampeonato());
        existente.setIsUltimoCampeaoDoTi(time.isIsUltimoCampeaoDoTi());
        return timeService.salvar(existente);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        timeService.excluirPorId(id);
    }
}
