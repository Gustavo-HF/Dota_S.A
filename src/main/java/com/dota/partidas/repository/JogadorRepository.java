package com.dota.partidas.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dota.partidas.model.Jogador;
@Repository
public interface JogadorRepository extends JpaRepository<Jogador, Long>{
    Optional<Jogador> findByNickname(String nickname);
    List<Jogador> findByNacionalidade(String nacionalidade);

}
