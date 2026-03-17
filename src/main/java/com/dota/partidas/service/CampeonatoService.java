package com.dota.partidas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dota.partidas.dto.CampeonatoDTO;
import com.dota.partidas.exception.CampeonatoException.CampeonatoNotFoundException;
import com.dota.partidas.exception.CampeonatoException.CampeonatoPartidasException;
import com.dota.partidas.exception.CampeonatoException.DataInvalidaException;
import com.dota.partidas.exception.CampeonatoException.PatchInvalidoException;
import com.dota.partidas.model.Campeonato;
import com.dota.partidas.repository.CampeonatoRepository;

@Service
public class CampeonatoService {
     
    @Autowired
    private CampeonatoRepository campeonatoRepository;

  
    public Campeonato salvarCampeonato(CampeonatoDTO campeonatodto){

      
        if (campeonatodto.getComecoCamp() == null || campeonatodto.getFimCamp() == null) {
            throw new DataInvalidaException("Datas de início e fim devem ser informadas");
        }
        if(campeonatodto.getComecoCamp().isAfter(campeonatodto.getFimCamp())){
            throw new DataInvalidaException("A data final não pode ser anterior a data inicial");
        }  
        // 2. Validar patch (regex já está no model, mas é bom reforçar)
        if (campeonatodto.getPatchCampeonato() == null || 
            !campeonatodto.getPatchCampeonato().matches("\\d\\.\\d{2}[a-z]")) {
            throw new PatchInvalidoException("O patch deve estar no formato oficial (ex: 7.33d)");
        }
        
        if(campeonatodto.getPartidas() == null || campeonatodto.getPartidas().isEmpty()){
            throw new CampeonatoPartidasException("Um campeonato deve possuir ao menos uma partida registrada");
        }
        
        Campeonato campeonato = new Campeonato();
        campeonato.setId(campeonatodto.getId());
        campeonato.setComecoCamp(campeonatodto.getComecoCamp()); 
        campeonato.setFimCamp(campeonatodto.getFimCamp());
        campeonato.setPatchCampeonato(campeonatodto.getPatchCampeonato());
        campeonato.setPartidas(campeonatodto.getPartidas());
        campeonato.setModo(campeonatodto.getModo());
        campeonato.setPartidas(campeonatodto.getPartidas());
        campeonato.setNome(campeonatodto.getNome());

        return campeonatoRepository.save(campeonato);
    }   

    public List<Campeonato> listarCampeonato(){
        return campeonatoRepository.findAll();
    }
    
    public Campeonato buscarPorId(Long id){
        return campeonatoRepository.findById(id).orElseThrow(() -> new CampeonatoNotFoundException("Campeonato não encontrado"));
    }

    public Campeonato atualizar(Long id, CampeonatoDTO dto) {
    Campeonato existente = campeonatoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Campeonato não encontrado"));

    existente.setNome(dto.getNome());
    existente.setComecoCamp(dto.getComecoCamp());
    existente.setFimCamp(dto.getFimCamp());
    existente.setPatchCampeonato(dto.getPatchCampeonato());
    existente.setPartidas(dto.getPartidas());

    return campeonatoRepository.save(existente);
}

    public void deletarCampeonato(){
        campeonatoRepository.deleteAll();
    }

    public void deletarCampeonatoPorId(Long id){
        campeonatoRepository.deleteById(id);
    }
} 


