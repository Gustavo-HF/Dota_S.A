package com.dota.partidas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dota.partidas.dto.PartidaDTO;
import com.dota.partidas.exception.CampeonatoException.CampeonatoNotFoundException;
import com.dota.partidas.exception.PartidaException.MvpPertencenteAPartidaException;
import com.dota.partidas.exception.PartidaException.PartidaNãoEncontradaException;
import com.dota.partidas.exception.TimeException.TimeDevePossuir5a10JogadoresException;
import com.dota.partidas.exception.TimeException.TimeNãoEncontradoException;
import com.dota.partidas.model.Campeonato;
import com.dota.partidas.model.Jogador;
import com.dota.partidas.model.Partida;
import com.dota.partidas.model.Time;
import com.dota.partidas.repository.CampeonatoRepository;
import com.dota.partidas.repository.JogadorRepository;
import com.dota.partidas.repository.PartidaRepository;
import com.dota.partidas.repository.TimeRepository;

@Service
public class PartidaService {

    @Autowired
    private PartidaRepository partidaRepository;

    @Autowired
    private TimeRepository timeRepository;

    @Autowired
    private CampeonatoRepository campeonatoRepository;

    @Autowired
    private JogadorRepository jogadorRepository;

    public Partida salvar(PartidaDTO dto) {
        // 1. BUSCAR as entidades reais no banco de dados
        Time timeA = timeRepository.findById(dto.getTimeA().getId())
                .orElseThrow(() -> new TimeNãoEncontradoException("Time A não encontrado"));

        Time timeB = timeRepository.findById(dto.getTimeB().getId())
                .orElseThrow(() -> new TimeNãoEncontradoException("Time B não encontrado"));

        Campeonato campeonato = campeonatoRepository.findById(dto.getCampeonato().getId())
                .orElseThrow(() -> new CampeonatoNotFoundException("Campeonato não encontrado"));

        // 2. AGORA use as variáveis 'timeA' e 'timeB' para validar (elas não são nulas)
        if (timeA.getNumeroJogadores() < 5 || timeB.getNumeroJogadores() < 5) {
            throw new TimeDevePossuir5a10JogadoresException("Os times devem ter no mínimo 5 jogadores");
        }

        Jogador mvp = jogadorRepository.findById(dto.getMvp().getId())
            .orElseThrow(() -> new MvpPertencenteAPartidaException("Jogador MVP não encontrado"));

        // 3. Montar o objeto Partida para salvar
        Partida partida = new Partida();
        partida.setTimeA(timeA);
        partida.setTimeB(timeB);
        partida.setCampeonato(campeonato);
        partida.setMvp(mvp);

        // Setar os outros campos simples do DTO
        partida.setDiferencaPatrimonioEquipes(dto.getDiferencaPatrimonioEquipes());
        partida.setData(dto.getData());
        partida.setDuracaoPartida(dto.getDuracaoPartida());
        partida.setPontuacao(dto.getPontuacao());
        partida.setPicks(dto.getPicks());
        partida.setBans(dto.getBans());

        // Não esqueça de tratar o MVP e a lista de jogadores se necessário!
        return partidaRepository.save(partida);
    }

    public List<Partida> listarTodas() {
        return partidaRepository.findAll();
    }

    public Partida buscarPorId(Long id) {
        return partidaRepository.findById(id)
                .orElseThrow(() -> new PartidaNãoEncontradaException("Partida não encontrada"));
    }

    public Partida atualizar(Long id, PartidaDTO dto) {
        Partida existente = partidaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Partida não encontrada"));

        existente.setDuracaoPartida(dto.getDuracaoPartida());
        existente.setPicks(dto.getPicks());
        existente.setBans(dto.getBans());
        existente.setMvp(dto.getMvp());
        existente.setTimeA(dto.getTimeA());
        existente.setTimeB(dto.getTimeB());
        existente.setCampeonato(dto.getCampeonato());

        return partidaRepository.save(existente);
    }

    public void excluirPorId(Long id) {
        partidaRepository.deleteById(id);
    }

    public void excluirTodas() {
        partidaRepository.deleteAll();
    }
}
