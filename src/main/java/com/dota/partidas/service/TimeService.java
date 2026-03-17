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

    // Salvar com validações
    public Time salvar(TimeDTO timeDTO) {
        // 1. Validar número de jogadores
        if (timeDTO.getNumeroJogadores() < 5 || timeDTO.getNumeroJogadores() > 10) {
            throw new TimeDevePossuir5a10JogadoresException("O time deve ter entre 5 e 10 jogadores");
        }

        // 2. Validar região
        if (timeDTO.getRegiao() == null || timeDTO.getRegiao().isBlank()) {
            throw new TimeDevePossuirRegiaoException("O time deve ter uma região registrada");
        }

        // 3. Validar classificação mundial única
        if (timeRepository.findByClassificacaoMundial(timeDTO.getClassificacaoMundial()).isPresent()) {
            throw new ClassificacaoMundialExistenteException("Já existe um time com essa classificação mundial");
        }

        // 4. Validar classificação de campeonato única
        if (timeRepository.findByClassificacaoCampeonato(timeDTO.getClassificacaoCampeonato()).isPresent()) {
            throw new ClassificacaoCampeonatoExistenteException("Já existe um time com essa classificação de campeonato");
        }

        // 5. Validar último campeão do TI (apenas um pode ser true)
        if (timeDTO.getIsUltimoCampeaoDoTi()) {
            List<Time> campeoes = timeRepository.findByIsUltimoCampeaoDoTiTrue();
            if (!campeoes.isEmpty()) {
                throw new CampeaoDoTIException("Já existe um time marcado como último campeão do TI");
            }
        }

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

    // Listar todos
    public List<Time> listarTodos() {
        return timeRepository.findAll();
    }

    // Buscar por ID
    public Time buscarPorId(Long id) {
        return timeRepository.findById(id)
                .orElseThrow(() -> new TimeNãoEncontradoException("Time não encontrado"));
    }

    public Time atualizar(Long id, TimeDTO dto) {
        Time existente = timeRepository.findById(id)
                .orElseThrow(() -> new TimeNãoEncontradoException("Time não encontrado"));

        existente.setNome(dto.getNome());
        existente.setNumeroJogadores(dto.getNumeroJogadores());
        existente.setRegiao(dto.getRegiao());
        existente.setClassificacaoMundial(dto.getClassificacaoMundial());
        existente.setClassificacaoCampeonato(dto.getClassificacaoCampeonato());
        existente.setIsUltimoCampeaoDoTi(dto.getIsUltimoCampeaoDoTi());

        return timeRepository.save(existente);
    }

    // Excluir por ID
    public void excluirPorId(Long id) {
        timeRepository.deleteById(id);
    }

    // Excluir todos
    public void excluirTodos() {
        timeRepository.deleteAll();
    }

}
