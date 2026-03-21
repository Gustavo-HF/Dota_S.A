package com.dota.partidas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dota.partidas.dto.TimeDTO;
import com.dota.partidas.exception.TimeException.CampeaoDoTIException;
import com.dota.partidas.exception.TimeException.ClassificacaoCampeonatoExistenteException;
import com.dota.partidas.exception.TimeException.ClassificacaoMundialExistenteException;
import com.dota.partidas.exception.TimeException.TimeDevePossuir5a10JogadoresException;
import com.dota.partidas.exception.TimeException.TimeDevePossuirRegiaoException;
import com.dota.partidas.exception.TimeException.TimeNãoEncontradoException;
import com.dota.partidas.model.Time;
import com.dota.partidas.repository.TimeRepository;

@Service
public class TimeService {

    @Autowired
    private TimeRepository timeRepository;

    /**
     * Registra um novo time, garantindo que ele cumpra os requisitos de
     * elegibilidade e respeite a hierarquia de rankings mundiais.
     */
    public Time salvar(TimeDTO timeDTO) {

        // 1. TAMANHO DO ELENCO: 
        // Um time de Dota 2 precisa de 5 jogadores, mas permitimos reservas (até 10).
        // Fora desse intervalo, o time é considerado inválido para competições oficiais.
        if (timeDTO.getNumeroJogadores() < 5 || timeDTO.getNumeroJogadores() > 10) {
            throw new TimeDevePossuir5a10JogadoresException("O time deve ter entre 5 e 10 jogadores");
        }

        // 2. IDENTIFICAÇÃO REGIONAL: 
        // A região (ex: SA, EU, SEA) é essencial para o matchmaking e organização de chaves.
        if (timeDTO.getRegiao() == null || timeDTO.getRegiao().isBlank()) {
            throw new TimeDevePossuirRegiaoException("O time deve ter uma região registrada");
        }

        // 3. INTEGRIDADE DO RANKING (Mundial e Campeonato): 
        // O ranking é uma lista ordenada. Se dois times tiverem a mesma posição, o ranking está quebrado.
        // Validamos a unicidade para garantir uma hierarquia clara.
        if (timeRepository.findByClassificacaoMundial(timeDTO.getClassificacaoMundial()).isPresent()) {
            throw new ClassificacaoMundialExistenteException("Já existe um time com essa classificação mundial");
        }

        if (timeRepository.findByClassificacaoCampeonato(timeDTO.getClassificacaoCampeonato()).isPresent()) {
            throw new ClassificacaoCampeonatoExistenteException("Já existe um time com essa classificação de campeonato");
        }

        // 4. REGRA DE ESTADO GLOBAL (O Campeão do TI): 
        // Existe apenas um "Aegis". Se este time está sendo marcado como o atual campeão,
        // garantimos que não haja outro time no banco ocupando esse trono simultaneamente.
        if (timeDTO.getIsUltimoCampeaoDoTi()) {
            List<Time> campeoes = timeRepository.findByIsUltimoCampeaoDoTiTrue();
            if (!campeoes.isEmpty()) {
                throw new CampeaoDoTIException("Já existe um time marcado como último campeão do TI");
            }
        }

        // Transferência de dados do DTO para o modelo de domínio
        Time time = new Time();
        time.setClassificacaoCampeonato(timeDTO.getClassificacaoCampeonato());
        time.setClassificacaoMundial(timeDTO.getClassificacaoMundial());
        time.setId(timeDTO.getId());
        time.setIsUltimoCampeaoDoTi(timeDTO.getIsUltimoCampeaoDoTi());
        time.setJogadores(timeDTO.getJogadores());
        time.setNome(timeDTO.getNome());
        time.setNumeroJogadores(timeDTO.getNumeroJogadores());
        time.setRegiao(timeDTO.getRegiao());

        return timeRepository.save(time);
    }

    /**
     * Lista todos os times cadastrados. Útil para exibir tabelas de
     * classificação geral.
     */
    public List<Time> listarTodos() {
        List<Time> times = timeRepository.findAll();

        if (times.isEmpty()) {
            throw new TimeNãoEncontradoException("Times não encontrados");
        }
        return times;
    }

    /**
     * Busca um time específico por seu identificador.
     */
    public Time buscarPorId(Long id) {
        return timeRepository.findById(id)
                .orElseThrow(() -> new TimeNãoEncontradoException("Time não encontrado"));
    }

    /**
     * Atualiza dados de performance e estrutura do time. Nota: Durante a
     * atualização, o JPA gerencia a mudança de estado da entidade.
     */
    public Time atualizar(Long id, TimeDTO dto) {
        Time existente = timeRepository.findById(id)
                .orElseThrow(() -> new TimeNãoEncontradoException("Time não encontrado"));

        // Atualização de campos mutáveis (Nome, Ranking, Região)
        existente.setNome(dto.getNome());
        existente.setNumeroJogadores(dto.getNumeroJogadores());
        existente.setRegiao(dto.getRegiao());
        existente.setClassificacaoMundial(dto.getClassificacaoMundial());
        existente.setClassificacaoCampeonato(dto.getClassificacaoCampeonato());
        existente.setIsUltimoCampeaoDoTi(dto.getIsUltimoCampeaoDoTi());

        return timeRepository.save(existente);
    }

    /**
     * Remove um time do sistema.
     */
    public void excluirPorId(Long id) {
        timeRepository.deleteById(id);
    }

    /**
     * Operação administrativa para resetar todos os times da base.
     */
    public void excluirTodos() {
        timeRepository.deleteAll();
    }
}
