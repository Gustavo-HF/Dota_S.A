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

import com.dota.partidas.dto.PartidaDTO;
import com.dota.partidas.model.Partida;
import com.dota.partidas.service.PartidaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/partidas")
public class PartidaController {

    @Autowired
    private PartidaService partidaService;

    /**
     * Lista todas as partidas registradas. Retorna Status 200 (OK). Ideal para
     * dashboards e tabelas de resultados.
     */
    @GetMapping
    public ResponseEntity<List<Partida>> listar() {
        return ResponseEntity.ok(partidaService.listarTodas());
    }

    /**
     * Busca os detalhes de uma partida específica (incluindo times, MVP e
     * picks/bans). Se o ID não for encontrado, o Service lançará uma exceção
     * capturada pelo fluxo.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Partida> buscarPorId(@Valid @PathVariable Long id) {
        Partida partida = partidaService.buscarPorId(id);

        // Verificação de segurança para retornar 404 caso o objeto venha nulo
        if (partida == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(partida);
    }

    /**
     * Registra uma nova partida no sistema. O @RequestBody converte o JSON
     * recebido no DTO que contém as referências dos times e campeonato. Retorna
     * 201 (Created) em caso de sucesso.
     */
    @PostMapping
    public ResponseEntity<Partida> salvar(@Valid @RequestBody PartidaDTO partida) {
        Partida salvo = partidaService.salvar(partida);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    /**
     * Atualiza dados de uma partida já existente (ex: alteração de picks, bans
     * ou duração). O bloco try-catch garante que tentativas de atualizar
     * partidas inexistentes retornem 404.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Partida> atualizar(
            @PathVariable Long id,
            @RequestBody PartidaDTO partida) {

        try {
            Partida atualizado = partidaService.atualizar(id, partida);
            return ResponseEntity.ok(atualizado);
        } catch (RuntimeException e) {
            // Caso o ID da URL não corresponda a nenhum registro no banco
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Exclui uma partida específica por ID. Antes de deletar, valida se o
     * recurso realmente existe para fornecer o feedback correto ao cliente.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@Valid @PathVariable Long id) {
        Partida existente = partidaService.buscarPorId(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        partidaService.excluirPorId(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Remove todos os registros de partidas (Operação de reset de banco).
     * Retorna 204 (No Content) após a limpeza.
     */
    @DeleteMapping("/all")
    public ResponseEntity<Void> excluirTudo() {
        partidaService.excluirTodas();
        return ResponseEntity.noContent().build();
    }

}
