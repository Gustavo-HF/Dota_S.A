package com.dota.partidas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dota.partidas.model.Jogador;
@Repository
public interface JogadorRepository extends JpaRepository<Jogador, Long>{
    
}
