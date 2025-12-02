package com.dota.partidas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dota.partidas.model.Time;
import com.dota.partidas.repository.TimeRepository;

@Service
public class TimeService {
    
    @Autowired
    private TimeRepository timeRepository;

    // Salvar com validações
    public Time salvar(Time time) {
        // 1. Validar número de jogadores
        if (time.getNumeroJogadores() < 5 || time.getNumeroJogadores() > 10) {
            throw new IllegalArgumentException("O time deve ter entre 5 e 10 jogadores");
        }

        // 2. Validar região
        if (time.getRegiao() == null || time.getRegiao().isBlank()) {
            throw new IllegalArgumentException("O time deve ter uma região registrada");
        }

        // 3. Validar classificação mundial única
        if (timeRepository.findByClassificacaoMundial(time.getClassificacaoMundial()).isPresent()) {
            throw new IllegalArgumentException("Já existe um time com essa classificação mundial");
        }

        // 4. Validar classificação de campeonato única
        if (timeRepository.findByClassificacaoCampeonato(time.getClassificacaoCampeonato()).isPresent()) {
            throw new IllegalArgumentException("Já existe um time com essa classificação de campeonato");
        }

        // 5. Validar último campeão do TI (apenas um pode ser true)
        if (time.isIsUltimoCampeaoDoTi()) {
            List<Time> campeoes = timeRepository.findByIsUltimoCampeaoDoTiTrue();
            if (!campeoes.isEmpty()) {
                throw new IllegalArgumentException("Já existe um time marcado como último campeão do TI");
            }
        }

        return timeRepository.save(time);
    }

    // Listar todos
    public List<Time> listarTodos() {
        return timeRepository.findAll();
    }

    // Buscar por ID
    public Time buscarPorId(Long id) {
        return timeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Time não encontrado"));
    }

    // Excluir por ID
    public void excluirPorId(Long id) {
        timeRepository.deleteById(id);
    }

    // Excluir todos
    public void excluirTodos() {
        timeRepository.deleteAll();
    }

}
