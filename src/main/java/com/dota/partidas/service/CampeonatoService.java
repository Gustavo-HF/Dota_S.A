package com.dota.partidas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dota.partidas.model.Campeonato;
import com.dota.partidas.repository.CampeonatoRepository;

@Service
public class CampeonatoService {
     
    @Autowired
    private CampeonatoRepository campeonatoRepository;

  
    public Campeonato salvarCampeonato(Campeonato campeonato){
        
         if (campeonato.getComecoCamp() == null || campeonato.getFimCamp() == null) {
            throw new IllegalArgumentException("Datas de início e fim devem ser informadas");
        }
        if(campeonato.getComecoCamp().isAfter(campeonato.getFimCamp())){
            throw new IllegalArgumentException("A data final não pode ser anterior a data inicial");
        }  
        // 2. Validar patch (regex já está no model, mas podemos reforçar)
        if (campeonato.getPatchCampeonato() == null || 
            !campeonato.getPatchCampeonato().matches("\\d\\.\\d{2}[a-z]")) {
            throw new IllegalArgumentException("O patch deve estar no formato oficial (ex: 7.33d)");
        }
        
        if(campeonato.getPartidas() == null || campeonato.getPartidas().isEmpty()){
            throw new IllegalArgumentException("Um campeonato deve possuir ao menos uma partida registrada");
        }
        return campeonatoRepository.save(campeonato);
    }   

    public List<Campeonato> listarCampeonato(){
        return campeonatoRepository.findAll();
    }
    
    public Campeonato buscarPorId(Long id){
        return campeonatoRepository.findById(id).orElseThrow(() -> new RuntimeException("Campeonato não encontrado"));
    }

    public void deletarCampeonato(){
        campeonatoRepository.deleteAll();
    }
} 


