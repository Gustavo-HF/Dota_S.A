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

    /**
     * Cria uma nova partida validando a existência de todos os componentes e a
     * viabilidade competitiva dos times.
     */
    public Partida salvar(PartidaDTO dto) {

        // 1. VALIDAÇÃO DE DEPENDÊNCIAS:
        // Antes de criar uma partida, precisamos garantir que o ecossistema existe.
        // Buscamos as entidades completas para evitar NullPointerException e garantir integridade.
        Time timeA = timeRepository.findById(dto.getTimeA().getId())
                .orElseThrow(() -> new TimeNãoEncontradoException("Time A não encontrado"));

        Time timeB = timeRepository.findById(dto.getTimeB().getId())
                .orElseThrow(() -> new TimeNãoEncontradoException("Time B não encontrado"));

        Campeonato campeonato = campeonatoRepository.findById(dto.getCampeonato().getId())
                .orElseThrow(() -> new CampeonatoNotFoundException("Campeonato não encontrado"));

        // 2. REGRA DE COMPETITIVIDADE:
        // Uma partida oficial não pode ocorrer se os times estiverem incompletos.
        // Validamos se ambos possuem o mínimo de 5 jogadores antes de permitir o registro.
        if (timeA.getNumeroJogadores() < 5 || timeB.getNumeroJogadores() < 5) {
            throw new TimeDevePossuir5a10JogadoresException("Os times devem ter no mínimo 5 jogadores");
        }

        // 3. RECONHECIMENTO INDIVIDUAL:
        // Buscamos o MVP (Most Valuable Player). É essencial que o jogador exista no banco.
        Jogador mvp = jogadorRepository.findById(dto.getMvp().getId())
                .orElseThrow(() -> new MvpPertencenteAPartidaException("Jogador MVP não encontrado"));

        // 4. MONTAGEM DA ENTIDADE:
        // Transferimos as referências das entidades buscadas e os dados primitivos do DTO.
        Partida partida = new Partida();
        partida.setTimeA(timeA);
        partida.setTimeB(timeB);
        partida.setCampeonato(campeonato);
        partida.setMvp(mvp);

        // Atribuição de métricas e detalhes técnicos da partida (Draft e Tempo)
        partida.setDiferencaPatrimonioEquipes(dto.getDiferencaPatrimonioEquipes());
        partida.setData(dto.getData());
        partida.setDuracaoPartida(dto.getDuracaoPartida());
        partida.setPontuacao(dto.getPontuacao());
        partida.setPicks(dto.getPicks()); // Lista de heróis escolhidos
        partida.setBans(dto.getBans());   // Lista de heróis banidos

        return partidaRepository.save(partida);
    }

    /**
     * Recupera o histórico global de partidas.
     */
    public List<Partida> listarTodas() {
        return partidaRepository.findAll();
    }

    /**
     * Busca os detalhes de uma partida específica por seu identificador único.
     */
    public Partida buscarPorId(Long id) {
        return partidaRepository.findById(id)
                .orElseThrow(() -> new PartidaNãoEncontradaException("Partida não encontrada"));
    }

    /**
     * Atualiza os dados de uma partida em andamento ou finalizada. Nota: Este
     * método permite a reatribuição de times e campeonato se necessário.
     */
    public Partida atualizar(Long id, PartidaDTO dto) {
        Partida existente = partidaRepository.findById(id)
                .orElseThrow(() -> new PartidaNãoEncontradaException("Partida não encontrada"));

        // Atualização de dados técnicos
        existente.setDuracaoPartida(dto.getDuracaoPartida());
        existente.setPicks(dto.getPicks());
        existente.setBans(dto.getBans());

        // Atualização de vínculos (Times, MVP e Campeonato)
        existente.setMvp(dto.getMvp());
        existente.setTimeA(dto.getTimeA());
        existente.setTimeB(dto.getTimeB());
        existente.setCampeonato(dto.getCampeonato());

        return partidaRepository.save(existente);
    }

    /**
     * Remove o registro de uma partida por ID.
     */
    public void excluirPorId(Long id) {
        partidaRepository.deleteById(id);
    }

    /**
     * Limpa todo o histórico de partidas (Operação de alto risco).
     */
    public void excluirTodas() {
        partidaRepository.deleteAll();
    }
}
