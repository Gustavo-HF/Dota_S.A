package com.dota.partidas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dota.partidas.exception.JogadorException.JogadorMMRMinimoException;
import com.dota.partidas.exception.JogadorException.JogadorMaximoHeroisMaisJogadosException;
import com.dota.partidas.exception.JogadorException.JogadorNicknameJaExistenteException;
import com.dota.partidas.exception.JogadorException.JogadorNotFoundException;
import com.dota.partidas.model.Jogador;
import com.dota.partidas.repository.JogadorRepository;

@Service
public class JogadorService {
    
    @Autowired
    private JogadorRepository jogadorRepository;

     // Salvar com validações
    public Jogador salvar(Jogador jogador) {
        // 1. Validar MMR
        if (jogador.getMmr() < 6000) {
            throw new  JogadorMMRMinimoException("O MMR mínimo para jogador profissional é 6000");
        }

        // 2. Validar nickname único
        if (jogadorRepository.findByNickname(jogador.getNickname()).isPresent()) {
            throw new JogadorNicknameJaExistenteException("Já existe um jogador com esse nickname");
        }

        // 3. Validar quantidade de heróis mais jogados
        if (jogador.getHeroisMaisJogados() != null && jogador.getHeroisMaisJogados().size() > 3) {
            throw new JogadorMaximoHeroisMaisJogadosException("Um jogador pode ter no máximo 3 heróis mais jogados");
        }

        return jogadorRepository.save(jogador);
    }

    // Listar todos
    public List<Jogador> listarTodos() {
        return jogadorRepository.findAll();
    }

    // Buscar por ID
    public Jogador buscarPorId(Long id) {
        return jogadorRepository.findById(id)
                .orElseThrow(() -> new JogadorNotFoundException("Jogador não encontrado"));
    }

    // Excluir por ID
    public void excluirPorId(Long id) {
        jogadorRepository.deleteById(id);
    }

    // Excluir todos
    public void excluirTodos() {
        jogadorRepository.deleteAll();
    }

}
