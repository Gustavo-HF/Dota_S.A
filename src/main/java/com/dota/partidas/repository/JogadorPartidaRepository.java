package com.dota.partidas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dota.partidas.model.JogadorPartida;
import com.dota.partidas.model.compositkeys.JogadorPartidaId;

@Repository
public interface  JogadorPartidaRepository extends JpaRepository<JogadorPartida, JogadorPartidaId> {
    
}
