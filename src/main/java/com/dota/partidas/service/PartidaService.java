package com.dota.partidas.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dota.partidas.exception.PartidaException.DuracaoDaPartidaMaiorQueZeroException;
import com.dota.partidas.exception.PartidaException.MvpPertencenteAPartidaException;
import com.dota.partidas.exception.PartidaException.PartidaDeveEstarVinculadaAUmCampeonatoException;
import com.dota.partidas.exception.PartidaException.PartidaNãoEncontradaException;
import com.dota.partidas.exception.PartidaException.QuantidadeDeBansPorPartidaIgualADezException;
import com.dota.partidas.exception.PartidaException.QuantidadeDePicksPorPartidaIgualADezException;
import com.dota.partidas.exception.PartidaException.TimeADeveTer5JogadoresException;
import com.dota.partidas.exception.PartidaException.TimeBDeveTer5JogadoresException;
import com.dota.partidas.model.Partida;
import com.dota.partidas.repository.PartidaRepository;

@Service
public class PartidaService {
    
    @Autowired
    private PartidaRepository partidaRepository;

    public Partida salvar(Partida partida) {
        // 1. Validar duração
        if (partida.getDuracaoPartida() == null || partida.getDuracaoPartida().toSecondOfDay() <= 0) {
            throw new DuracaoDaPartidaMaiorQueZeroException("A duração da partida deve ser maior que zero segundos");
        }

        // 2. Validar picks e bans
        if (partida.getPicks() == null || partida.getPicks().size() != 10) {
            throw new QuantidadeDePicksPorPartidaIgualADezException("A partida deve ter exatamente 10 picks (5 por time)");
        }
        if (partida.getBans() == null || partida.getBans().size() != 10) {
            throw new QuantidadeDeBansPorPartidaIgualADezException("A partida deve ter exatamente 10 bans (5 por time)");
        }

        // 3. Validar MVP
        if (partida.getMvp() == null || 
            partida.getJogadores().stream().noneMatch(jp -> jp.getJogador().equals(partida.getMvp()))) {
            throw new MvpPertencenteAPartidaException("O MVP deve ser um jogador que participou da partida");
        }

        // 4. Validar times com pelo menos 5 jogadores
        if (partida.getTimeA() == null || partida.getTimeA().getNumeroJogadores() < 5) {
            throw new TimeADeveTer5JogadoresException("O time A deve ter pelo menos 5 jogadores");
        }
        if (partida.getTimeB() == null || partida.getTimeB().getNumeroJogadores() < 5) {
            throw new TimeBDeveTer5JogadoresException("O time B deve ter pelo menos 5 jogadores");
        }

        // 5. Validar campeonato vinculado
        if (partida.getCampeonato() == null) {
            throw new PartidaDeveEstarVinculadaAUmCampeonatoException("A partida deve estar vinculada a um campeonato");
        }

        return partidaRepository.save(partida);
    }

    public List<Partida> listarTodas() {
        return partidaRepository.findAll();
    }

    public Partida buscarPorId(Long id) {
        return partidaRepository.findById(id)
                .orElseThrow(() -> new PartidaNãoEncontradaException("Partida não encontrada"));
    }

    public void excluirPorId(Long id) {
        partidaRepository.deleteById(id);
    }

    public void excluirTodas() {
        partidaRepository.deleteAll();
    }
}

