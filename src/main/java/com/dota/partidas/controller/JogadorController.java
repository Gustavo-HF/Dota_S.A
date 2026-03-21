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

import com.dota.partidas.dto.JogadorDTO;
import com.dota.partidas.model.Jogador;
import com.dota.partidas.service.JogadorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/jogadores")
public class JogadorController {

    @Autowired
    private JogadorService jogadorService;

    /**
     * Retorna a lista de todos os jogadores profissionais cadastrados. Status
     * 200 (OK) em caso de sucesso.
     */
    @GetMapping
    public ResponseEntity<List<Jogador>> listar() {
        return ResponseEntity.ok(jogadorService.listarTodos());
    }

    /**
     * Busca o perfil detalhado de um jogador pelo ID. Caso o jogador não
     * exista, o Service lança uma exceção tratada aqui para retornar 404 (Not
     * Found).
     */
    @GetMapping("/{id}")
    public ResponseEntity<Jogador> buscarPorId(@Valid @PathVariable Long id) {
        Jogador jogador = jogadorService.buscarPorId(id);

        // Verificação defensiva para garantir que IDs inexistentes não retornem 200 com corpo vazio
        if (jogador == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(jogador);
    }

    /**
     * Registra um novo jogador. O @Valid dispara as validações do Bean
     * Validation no DTO antes de entrar na lógica de negócio. Retorna 201
     * (Created) e o objeto persistido.
     */
    @PostMapping
    public ResponseEntity<Jogador> salvar(@Valid @RequestBody JogadorDTO jogador) {
        Jogador salvo = jogadorService.salvar(jogador);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    /**
     * Atualiza os dados de um jogador (MMR, Time, Nickname, etc). O bloco
     * try-catch lida com tentativas de atualizar jogadores que não constam na
     * base.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Jogador> atualizar(
            @PathVariable Long id,
            @RequestBody JogadorDTO jogador) {

        try {
            Jogador atualizado = jogadorService.atualizar(id, jogador);
            return ResponseEntity.ok(atualizado);
        } catch (RuntimeException e) {
            // Intercepta a falha caso o ID informado na URL seja inválido
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Remove um jogador específico por ID. Verifica a existência antes da
     * deleção para garantir o feedback correto (404 ou 204).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@Valid @PathVariable Long id) {
        Jogador existente = jogadorService.buscarPorId(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        jogadorService.excluirPorId(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Remove todos os jogadores da base de dados. Endpoint potente para
     * manutenção ou reset de ambiente de testes.
     */
    @DeleteMapping("/all")
    public ResponseEntity<Void> excluirTudo() {
        jogadorService.excluirTodos();
        return ResponseEntity.noContent().build();
    }
}
