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

import com.dota.partidas.model.Time;
import com.dota.partidas.service.TimeService;

@RestController
@RequestMapping("/times")
public class TimeController {

    @Autowired
    private TimeService timeService;

    // Listar todos
    @GetMapping
    public ResponseEntity<List<Time>> listar() {
        return ResponseEntity.ok(timeService.listarTodos());
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Time> buscarPorId(@PathVariable Long id) {
        Time time = timeService.buscarPorId(id);
        if (time == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(time);
    }

    // Salvar
    @PostMapping
    public ResponseEntity<Time> salvar(@RequestBody Time time) {
        Time salvo = timeService.salvar(time);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    // Atualizar
    @PutMapping("/{id}")
    public ResponseEntity<Time> atualizar(@PathVariable Long id, @RequestBody Time time) {
        Time existente = timeService.buscarPorId(id);
        if (existente == null) return ResponseEntity.notFound().build();

        existente.setNome(time.getNome());
        existente.setNumeroJogadores(time.getNumeroJogadores());
        existente.setRegiao(time.getRegiao());
        existente.setClassificacaoMundial(time.getClassificacaoMundial());
        existente.setClassificacaoCampeonato(time.getClassificacaoCampeonato());
        existente.setIsUltimoCampeaoDoTi(time.isIsUltimoCampeaoDoTi());

        Time atualizado = timeService.salvar(existente);
        return ResponseEntity.ok(atualizado);
    }

    // Excluir por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        Time existente = timeService.buscarPorId(id);
        if (existente == null) return ResponseEntity.notFound().build();
        timeService.excluirPorId(id);
        return ResponseEntity.noContent().build();
    }

    // Excluir todos
    @DeleteMapping("/all")
    public ResponseEntity<Void> excluirTodos() {
        timeService.excluirTodos();
        return ResponseEntity.noContent().build();
    }
}

