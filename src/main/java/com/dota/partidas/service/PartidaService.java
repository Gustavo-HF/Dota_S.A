package com.dota.partidas.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dota.partidas.dto.PartidaDTO;
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

    public Partida salvar(PartidaDTO partidaDTO) {
        // 1. Validar duração
        if (partidaDTO.getDuracaoPartida() == null || partidaDTO.getDuracaoPartida().toSecondOfDay() <= 0) {
            throw new DuracaoDaPartidaMaiorQueZeroException("A duração da partida deve ser maior que zero segundos");
        }

        // 2. Validar picks e bans
        if (partidaDTO.getPicks() == null || partidaDTO.getPicks().size() != 10) {
            throw new QuantidadeDePicksPorPartidaIgualADezException("A partida deve ter exatamente 10 picks (5 por time)");
        }
        if (partidaDTO.getBans() == null || partidaDTO.getBans().size() != 10) {
            throw new QuantidadeDeBansPorPartidaIgualADezException("A partida deve ter exatamente 10 bans (5 por time)");
        }

        // 3. Validar MVP
        if (partidaDTO.getMvp() == null || 
            partidaDTO.getJogadores().stream().noneMatch(jp -> jp.getJogador().equals(partidaDTO.getMvp()))) {
            throw new MvpPertencenteAPartidaException("O MVP deve ser um jogador que participou da partida");
        }

        // 4. Validar times com pelo menos 5 jogadores
        if (partidaDTO.getTimeA() == null || partidaDTO.getTimeA().getNumeroJogadores() < 5) {
            throw new TimeADeveTer5JogadoresException("O time A deve ter pelo menos 5 jogadores");
        }
        if (partidaDTO.getTimeB() == null || partidaDTO.getTimeB().getNumeroJogadores() < 5) {
            throw new TimeBDeveTer5JogadoresException("O time B deve ter pelo menos 5 jogadores");
        }

        // 5. Validar campeonato vinculado
        if (partidaDTO.getCampeonato() == null) {
            throw new PartidaDeveEstarVinculadaAUmCampeonatoException("A partida deve estar vinculada a um campeonato");
        }

        Partida partida = new Partida();
        partida.setId(partidaDTO.getId());
        partida.setTimeA(partidaDTO.getTimeA());
        partida.setTimeB(partidaDTO.getTimeB());
        partida.setJogadores(partidaDTO.getJogadores());
        partida.setMvp(partidaDTO.getMvp());
        partida.setPicks(partidaDTO.getPicks());
        partida.setBans(partidaDTO.getBans());
        partida.setPontuacao(partidaDTO.getPontuacao());
        partida.setDuracaoPartida(partidaDTO.getDuracaoPartida());
        partida.setDiferencaPatrimonioEquipes(partidaDTO.getDiferencaPatrimonioEquipes());
        partida.setCampeonato(partidaDTO.getCampeonato());
        
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

