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

import com.dota.partidas.dto.TimeDTO;
import com.dota.partidas.model.Time;
import com.dota.partidas.service.TimeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/times")
public class TimeController {

    @Autowired
    private TimeService timeService;

    /**
     * Retorna a lista de todas as organizações (times) cadastradas. Utiliza o
     * status 200 (OK) para confirmar o sucesso da requisição.
     */
    @GetMapping
    public ResponseEntity<List<Time>> listar() {
        return ResponseEntity.ok(timeService.listarTodos());
    }

    /**
     * Busca um time específico pelo seu identificador único. Se o Service não
     * encontrar o registro, o fluxo é interrompido para retornar 404 (Not
     * Found).
     */
    @GetMapping("/{id}")
    public ResponseEntity<Time> buscarPorId(@Valid @PathVariable Long id) {
        Time time = timeService.buscarPorId(id);

        // Verificação defensiva: garante que o cliente receba o status correto caso o ID seja inválido.
        if (time == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(time);
    }

    /**
     * Registra um novo time no sistema. Recebe um TimeDTO e, após passar pelas
     * validações (@Valid), persiste a nova entidade. Retorna 201 (Created)
     * seguindo as boas práticas para criação de recursos.
     */
    @PostMapping
    public ResponseEntity<Time> salvar(@Valid @RequestBody TimeDTO time) {
        Time salvo = timeService.salvar(time);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    /**
     * Atualiza as informações de um time existente (ex: mudança de região ou
     * ranking). O bloco try-catch captura erros caso o ID fornecido na URL não
     * exista na base.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Time> atualizar(
            @PathVariable Long id,
            @RequestBody TimeDTO time) {

        try {
            Time atualizado = timeService.atualizar(id, time);
            return ResponseEntity.ok(atualizado);
        } catch (RuntimeException e) {
            // Caso o ID no PathVariable não seja encontrado pelo Service
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Remove um time do sistema através do ID. Realiza uma verificação prévia
     * de existência para garantir o feedback apropriado (404 ou 204).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@Valid @PathVariable Long id) {
        Time existente = timeService.buscarPorId(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        timeService.excluirPorId(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Endpoint administrativo para exclusão em massa de todos os times. Retorna
     * 204 (No Content) após a limpeza bem-sucedida.
     */
    @DeleteMapping("/all")
    public ResponseEntity<Void> excluirTodos() {
        timeService.excluirTodos();
        return ResponseEntity.noContent().build();
    }
}
