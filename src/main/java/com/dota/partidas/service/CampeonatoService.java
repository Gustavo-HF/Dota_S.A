package com.dota.partidas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dota.partidas.dto.CampeonatoDTO;
import com.dota.partidas.exception.CampeonatoException.CampeonatoNotFoundException;
import com.dota.partidas.exception.CampeonatoException.CampeonatoPartidasException;
import com.dota.partidas.exception.CampeonatoException.DataInvalidaException;
import com.dota.partidas.exception.CampeonatoException.PatchInvalidoException;
import com.dota.partidas.model.Campeonato;
import com.dota.partidas.repository.CampeonatoRepository;

@Service
public class CampeonatoService {

    @Autowired
    private CampeonatoRepository campeonatoRepository;

    /**
     * Registra um novo campeonato no sistema. Aplica validações de data,
     * formato de patch e obrigatoriedade de partidas.
     */
    public Campeonato salvarCampeonato(CampeonatoDTO campeonatodto) {

        // Validação de Datas: Impede registros sem período definido ou com lógica invertida
        if (campeonatodto.getComecoCamp() == null || campeonatodto.getFimCamp() == null) {
            throw new DataInvalidaException("Datas de início e fim devem ser informadas");
        }
        if (campeonatodto.getComecoCamp().isAfter(campeonatodto.getFimCamp())) {
            throw new DataInvalidaException("A data final não pode ser anterior a data inicial");
        }

        // Validação de Patch: Garante que a versão siga o padrão técnico (ex: 7.33d)
        // Isso evita sujeira nos dados e erros de integração com APIs externas
        if (campeonatodto.getPatchCampeonato() == null
                || !campeonatodto.getPatchCampeonato().matches("\\d\\.\\d{2}[a-z]")) {
            throw new PatchInvalidoException("O patch deve estar no formato oficial (ex: 7.33d)");
        }

        // Regra de Negócio: Um campeonato vazio não tem valor estatístico, por isso exigimos partidas
        if (campeonatodto.getPartidas() == null || campeonatodto.getPartidas().isEmpty()) {
            throw new CampeonatoPartidasException("Um campeonato deve possuir ao menos uma partida registrada");
        }

        // Mapeamento manual do DTO para a Entidade persistente
        Campeonato campeonato = new Campeonato();
        campeonato.setId(campeonatodto.getId());
        campeonato.setComecoCamp(campeonatodto.getComecoCamp());
        campeonato.setFimCamp(campeonatodto.getFimCamp());
        campeonato.setPatchCampeonato(campeonatodto.getPatchCampeonato());
        campeonato.setPartidas(campeonatodto.getPartidas());
        campeonato.setModo(campeonatodto.getModo());
        campeonato.setNome(campeonatodto.getNome());

        return campeonatoRepository.save(campeonato);
    }

    /**
     * Retorna todos os campeonatos cadastrados.
     */
    public List<Campeonato> listarCampeonato() {
        return campeonatoRepository.findAll();
    }

    /**
     * Busca um campeonato específico. Lança exceção customizada caso o ID não
     * exista para facilitar o tratamento no Controller.
     */
    public Campeonato buscarPorId(Long id) {
        return campeonatoRepository.findById(id)
                .orElseThrow(() -> new CampeonatoNotFoundException("Campeonato não encontrado"));
    }

    /**
     * Atualiza um campeonato existente. Recupera o objeto do banco e aplica as
     * alterações vindas do DTO.
     */
    public Campeonato atualizar(Long id, CampeonatoDTO dto) {
        Campeonato existente = campeonatoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Campeonato não encontrado"));

        // Atualização dos campos permitidos
        existente.setNome(dto.getNome());
        existente.setComecoCamp(dto.getComecoCamp());
        existente.setFimCamp(dto.getFimCamp());
        existente.setPatchCampeonato(dto.getPatchCampeonato());
        existente.setPartidas(dto.getPartidas());

        return campeonatoRepository.save(existente);
    }

    /**
     * Remove todos os registros da tabela (Operação crítica).
     */
    public void deletarCampeonato() {
        campeonatoRepository.deleteAll();
    }

    /**
     * Remove um campeonato específico por ID.
     */
    public void deletarCampeonatoPorId(Long id) {
        campeonatoRepository.deleteById(id);
    }
}
