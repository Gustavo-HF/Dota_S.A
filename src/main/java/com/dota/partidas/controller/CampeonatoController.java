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

import com.dota.partidas.dto.CampeonatoDTO;
import com.dota.partidas.model.Campeonato;
import com.dota.partidas.service.CampeonatoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/campeonatos")
public class CampeonatoController {

    @Autowired
    private CampeonatoService campeonatoService;

    /**
     * Endpoint para listagem global. Retorna Status 200 (OK) com a lista de
     * todos os campeonatos.
     */
    @GetMapping
    public ResponseEntity<List<Campeonato>> listar() {
        return ResponseEntity.ok(campeonatoService.listarCampeonato());
    }

    /**
     * Busca um campeonato por seu ID único. Caso o ID não exista, o Service
     * lança uma exceção que resulta em 404 (Not Found).
     */
    @GetMapping("/{id}")
    public ResponseEntity<Campeonato> buscarPorId(@Valid @PathVariable Long id) {
        Campeonato campeonato = campeonatoService.buscarPorId(id);

        // Verificação de segurança: garante que se o service retornar null, o cliente receba 404.
        if (campeonato == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(campeonato);
    }

    /**
     * Cria um novo campeonato.
     *
     * @RequestBody garante que os dados enviados no JSON sejam convertidos para
     * o DTO. Retorna Status 201 (Created) em caso de sucesso, conforme as boas
     * práticas REST.
     */
    @PostMapping
    public ResponseEntity<Campeonato> salvar(@Valid @RequestBody CampeonatoDTO campeonatoDTO) {
        Campeonato salvo = campeonatoService.salvarCampeonato(campeonatoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    /**
     * Atualiza os dados de um campeonato existente via ID. Utiliza um bloco
     * try-catch para interceptar falhas de busca e retornar 404 de forma limpa.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Campeonato> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody CampeonatoDTO campeonato) {

        try {
            Campeonato atualizado = campeonatoService.atualizar(id, campeonato);
            return ResponseEntity.ok(atualizado);
        } catch (RuntimeException e) {
            // Caso o ID passado na URL não exista no banco de dados
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Remove um campeonato específico. Retorna Status 204 (No Content)
     * indicando que a operação foi realizada e não há corpo na resposta.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirPorId(@Valid @PathVariable Long id) {
        Campeonato existente = campeonatoService.buscarPorId(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        campeonatoService.deletarCampeonatoPorId(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Endpoint administrativo para limpeza total da tabela de campeonatos.
     * Retorna 204 (No Content) após a deleção em massa.
     */
    @DeleteMapping("/all")
    public ResponseEntity<Void> excluir() {
        campeonatoService.deletarCampeonato();
        return ResponseEntity.noContent().build();
    }
}
