package com.dota.partidas.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Campeonato {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)

    private Long id;
    private String nome;
    private String modo;
    private String patchCampeonato;
    private String comecoCamp;
    private String fimCamp;

    @OneToMany(mappedBy="partida")
    List<Partida>partidas;

    public Campeonato(){

    }

    public Campeonato(String comecoCamp, String fimCamp, Long id, String modo, String nome, List<Partida> partidas, String patchCampeonato) {
        this.comecoCamp = comecoCamp;
        this.fimCamp = fimCamp;
        this.id = id;
        this.modo = modo;
        this.nome = nome;
        this.partidas = partidas;
        this.patchCampeonato = patchCampeonato;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getModo() {
        return modo;
    }

    public void setModo(String modo) {
        this.modo = modo;
    }

    public String getPatchCampeonato() {
        return patchCampeonato;
    }

    public void setPatchCampeonato(String patchCampeonato) {
        this.patchCampeonato = patchCampeonato;
    }

    public String getComecoCamp() {
        return comecoCamp;
    }

    public void setComecoCamp(String comecoCamp) {
        this.comecoCamp = comecoCamp;
    }

    public String getFimCamp() {
        return fimCamp;
    }

    public void setFimCamp(String fimCamp) {
        this.fimCamp = fimCamp;
    }

    public List<Partida> getPartidas() {
        return partidas;
    }

    public void setPartidas(List<Partida> partidas) {
        this.partidas = partidas;
    }


}
