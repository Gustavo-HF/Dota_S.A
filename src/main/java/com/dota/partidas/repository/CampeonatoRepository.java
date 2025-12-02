package com.dota.partidas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dota.partidas.model.Campeonato;

@Repository
public interface CampeonatoRepository extends JpaRepository<Campeonato, Long> {
    List<Campeonato> findByNome(String nome);
    List<Campeonato> findByModo(String modo);
}
