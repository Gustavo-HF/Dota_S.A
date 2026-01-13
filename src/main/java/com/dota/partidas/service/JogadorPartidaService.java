package com.dota.partidas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public JogadorPartida salvar(JogadorPartida jogadorPartida) {
        // 1. Validar se o jogador já está vinculado a essa partida
        boolean jogadorJaNaPartida = jogadorPartidaRepository
                .findByPartidaAndJogador(jogadorPartida.getPartida(), jogadorPartida.getJogador())
                .isPresent();

        if (jogadorJaNaPartida) {
            throw new JogadorJaNaPartidaException("O jogador já está vinculado a esta partida");
        }

        // 2. Validar se o jogador pertence a um dos times da partida
        if (!(jogadorPartida.getPartida().getTimeA().getJogadores().contains(jogadorPartida.getJogador())
                || jogadorPartida.getPartida().getTimeB().getJogadores().contains(jogadorPartida.getJogador()))) {
            throw new JogadorDevePertencerAPartidaException("O jogador deve pertencer a um dos times que disputou a partida");
        }
        if (!jogadorPartida.getKda().matches("\\d+/\\d+/\\d+")) {
        throw new JogadorFormatoKDAExcpetion("O KDA deve estar no formato Abates/Mortes/Assistências, ex: 10/2/15");
        }


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



