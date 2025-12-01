package com.dota.partidas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dota.partidas.model.Campeonato;
@Repository
public interface CampeonatoRepository extends JpaRepository<Campeonato, Long> {

}
