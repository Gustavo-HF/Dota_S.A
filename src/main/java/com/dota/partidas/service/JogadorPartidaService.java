package com.dota.partidas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dota.partidas.dto.JogadorPartidaDTO;

import com.dota.partidas.exception.JogadorPartidaException.JogadorDevePertencerAPartidaException;
import com.dota.partidas.exception.JogadorPartidaException.JogadorFormatoKDAExcpetion;
import com.dota.partidas.exception.JogadorPartidaException.JogadorJaNaPartidaException;
import com.dota.partidas.exception.JogadorPartidaException.JogadorPartidaNotFoundException;
import com.dota.partidas.exception.JogadorPartidaException.JogadorPartidaRegistroExcpetion;
import com.dota.partidas.model.Jogador;
import com.dota.partidas.model.JogadorPartida;
import com.dota.partidas.model.Partida;
import com.dota.partidas.model.compositkeys.JogadorPartidaId;
import com.dota.partidas.repository.JogadorPartidaRepository;
import com.dota.partidas.repository.PartidaRepository;
import com.dota.partidas.repository.JogadorRepository;

@Service
public class JogadorPartidaService {

    @Autowired
    PartidaRepository partidaRepository;

    @Autowired
    JogadorRepository jogadorRepository;

    @Autowired
    private JogadorPartidaRepository jogadorPartidaRepository;

    public JogadorPartida salvar(JogadorPartidaDTO dto) {
        // 1. BUSCAR entidades completas do banco
        Partida partida = partidaRepository.findById(dto.getPartida().getId())
                .orElseThrow(() -> new RuntimeException("Partida não encontrada"));

        Jogador jogador = jogadorRepository.findById(dto.getJogador().getId())
                .orElseThrow(() -> new RuntimeException("Jogador não encontrado"));

        // 2. AGORA as validações funcionam porque os objetos não são nulos
        boolean jogadorJaNaPartida = jogadorPartidaRepository
                .findByPartidaAndJogador(partida, jogador)
                .isPresent();

        if (jogadorJaNaPartida) {
            throw new JogadorJaNaPartidaException("O jogador já está vinculado a esta partida");
        }

        // 3. Validar se o jogador pertence aos times (usando os objetos do banco)
        boolean pertenceAoTimeA = partida.getTimeA().getJogadores().contains(jogador);
        boolean pertenceAoTimeB = partida.getTimeB().getJogadores().contains(jogador);

        if (!pertenceAoTimeA && !pertenceAoTimeB) {
            throw new JogadorDevePertencerAPartidaException("O jogador deve pertencer a um dos times da partida");
        }

        // 4. Validar KDA
        if (!dto.getKda().matches("\\d+/\\d+/\\d+")) {
            throw new JogadorFormatoKDAExcpetion("Formato de KDA inválido (ex: 10/2/15)");
        }

        // 5. Salvar
        JogadorPartida jogadorPartida = new JogadorPartida();
        jogadorPartida.setJogador(jogador);
        jogadorPartida.setPartida(partida);
        jogadorPartida.setKda(dto.getKda());
        jogadorPartida.setPatrimonioLiquidoIndividual(dto.getPatrimonioLiquidoIndividual());

        return jogadorPartidaRepository.save(jogadorPartida);
    }

    public List<JogadorPartida> listarTodos() {
        return jogadorPartidaRepository.findAll();
    }

    public JogadorPartida buscarPorId(JogadorPartidaId id) {
        return jogadorPartidaRepository.findById(id)
                .orElseThrow(() -> new JogadorPartidaRegistroExcpetion("Registro de JogadorPartida não encontrado"));
    }

    public JogadorPartida atualizar(JogadorPartidaId id, JogadorPartidaDTO dto) {
        JogadorPartida existente = jogadorPartidaRepository.findById(id)
                .orElseThrow(() -> new JogadorPartidaNotFoundException("Registro não encontrado"));

        existente.setKda(dto.getKda());

        return jogadorPartidaRepository.save(existente);
    }

    public void excluirPorId(JogadorPartidaId id) {
        jogadorPartidaRepository.deleteById(id);
    }

    public void excluirTodos() {
        jogadorPartidaRepository.deleteAll();
    }
}
