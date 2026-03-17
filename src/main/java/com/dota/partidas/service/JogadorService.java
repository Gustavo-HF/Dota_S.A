package com.dota.partidas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dota.partidas.dto.JogadorDTO;
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
    public Jogador salvar(JogadorDTO jogadorDTO) {
        // 1. Validar MMR
        if (jogadorDTO.getMmr() < 6000) {
            throw new JogadorMMRMinimoException("O MMR mínimo para jogador profissional é 6000");
        }

        // 2. Validar nickname único
        if (jogadorRepository.findByNickname(jogadorDTO.getNickname()).isPresent()) {
            throw new JogadorNicknameJaExistenteException("Já existe um jogador com esse nickname");
        }

        // 3. Validar quantidade de heróis mais jogados
        if (jogadorDTO.getHeroisMaisJogados() != null && jogadorDTO.getHeroisMaisJogados().size() > 3) {
            throw new JogadorMaximoHeroisMaisJogadosException("Um jogador pode ter no máximo 3 heróis mais jogados");
        }

        Jogador jogador = new Jogador();
        jogador.setId(jogadorDTO.getId());
        jogador.setNome(jogadorDTO.getNome());
        jogador.setFuncao(jogadorDTO.getFuncao());
        jogador.setHeroisMaisJogados(jogadorDTO.getHeroisMaisJogados());
        jogador.setNacionalidade(jogadorDTO.getNacionalidade());
        jogador.setMmr(jogadorDTO.getMmr());
        jogador.setPosicao(jogadorDTO.getPosicao());
        jogador.setNickname(jogadorDTO.getNickname());

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

    public Jogador atualizar(Long id, JogadorDTO dto) {
        Jogador existente = jogadorRepository.findById(id)
                .orElseThrow(() -> new JogadorNotFoundException("Jogador não encontrado"));

        existente.setNickname(dto.getNickname());
        existente.setMmr(dto.getMmr());
        existente.setHeroisMaisJogados(dto.getHeroisMaisJogados());

        return jogadorRepository.save(existente);
    }

    // Excluir por ID
    public void excluirPorId(Long id) {
        Jogador jogador = jogadorRepository.findById(id)
                .orElseThrow(() -> new JogadorNotFoundException("Jogador não encontrado"));

        jogadorRepository.delete(jogador);
    }

    // Excluir todos
    public void excluirTodos() {
        jogadorRepository.deleteAll();
    }

}
