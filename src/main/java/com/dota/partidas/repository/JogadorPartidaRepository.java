package com.dota.partidas.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dota.partidas.model.Jogador;
import com.dota.partidas.model.JogadorPartida;
import com.dota.partidas.model.Partida;
import com.dota.partidas.model.compositkeys.JogadorPartidaId;

@Repository
public interface  JogadorPartidaRepository extends JpaRepository<JogadorPartida, JogadorPartidaId> {
     Optional<JogadorPartida> findByPartidaAndJogador(Partida partida, Jogador jogador);

}
