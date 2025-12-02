package com.dota.partidas.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dota.partidas.model.Time;
@Repository
public interface  TimeRepository extends JpaRepository<Time, Long> {
    Optional<Time> findByClassificacaoMundial(Integer classificacaoMundial);
    Optional<Time> findByClassificacaoCampeonato(Integer classificacaoCampeonato);
    List<Time> findByIsUltimoCampeaoDoTiTrue();
    List<Time> findByRegiao(String regiao);


}
