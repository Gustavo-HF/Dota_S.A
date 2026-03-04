package com.dota.partidas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dota.partidas.dto.JogadorPartidaDTO;
import com.dota.partidas.exception.JogadorPartidaException.JogadorDevePertencerAPartidaException;
import com.dota.partidas.exception.JogadorPartidaException.JogadorFormatoKDAExcpetion;
import com.dota.partidas.exception.JogadorPartidaException.JogadorJaNaPartidaException;
import com.dota.partidas.exception.JogadorPartidaException.JogadorPartidaRegistroExcpetion;
import com.dota.partidas.model.JogadorPartida;
import com.dota.partidas.model.compositkeys.JogadorPartidaId;
import com.dota.partidas.repository.JogadorPartidaRepository;

@Service
public class JogadorPartidaService {
    
    @Autowired
    private JogadorPartidaRepository jogadorPartidaRepository;

    public JogadorPartida salvar(JogadorPartidaDTO jogadorPartidadDTO) {
        // 1. Validar se o jogador já está vinculado a essa partida
        boolean jogadorJaNaPartida = jogadorPartidaRepository
                .findByPartidaAndJogador(jogadorPartidadDTO.getPartida(), jogadorPartidadDTO.getJogador())
                .isPresent();

        if (jogadorJaNaPartida) {
            throw new JogadorJaNaPartidaException("O jogador já está vinculado a esta partida");
        }

        // 2. Validar se o jogador pertence a um dos times da partida
        if (!(jogadorPartidadDTO.getPartida().getTimeA().getJogadores().contains(jogadorPartidadDTO.getJogador())
                || jogadorPartidadDTO.getPartida().getTimeB().getJogadores().contains(jogadorPartidadDTO.getJogador()))) {
            throw new JogadorDevePertencerAPartidaException("O jogador deve pertencer a um dos times que disputou a partida");
        }
        if (!jogadorPartidadDTO.getKda().matches("\\d+/\\d+/\\d+")) {
        throw new JogadorFormatoKDAExcpetion("O KDA deve estar no formato Abates/Mortes/Assistências, ex: 10/2/15");
        }

        JogadorPartida jogadorPartida = new JogadorPartida();
        jogadorPartida.setJogador(jogadorPartidadDTO.getJogador());
        jogadorPartida.setKda(jogadorPartidadDTO.getKda());
        jogadorPartida.setPartida(jogadorPartidadDTO.getPartida());
        jogadorPartida.setPatrimonioLiquidoIndividual(jogadorPartidadDTO.getPatrimonioLiquidoIndividual());
        
        return jogadorPartidaRepository.save(jogadorPartida);
    }

    public List<JogadorPartida> listarTodos() {
        return jogadorPartidaRepository.findAll();
    }

    public JogadorPartida buscarPorId(JogadorPartidaId id) {
        return jogadorPartidaRepository.findById(id)
                .orElseThrow(() -> new JogadorPartidaRegistroExcpetion("Registro de JogadorPartida não encontrado"));
    }

    public void excluirPorId(JogadorPartidaId id) {
        jogadorPartidaRepository.deleteById(id);
    }

    public void excluirTodos() {
        jogadorPartidaRepository.deleteAll();
    }
}



