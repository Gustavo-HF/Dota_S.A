package com.dota.partidas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dota.partidas.model.Time;
@Repository
public interface  TimeRepository extends JpaRepository<Time, Long> {
    
}
