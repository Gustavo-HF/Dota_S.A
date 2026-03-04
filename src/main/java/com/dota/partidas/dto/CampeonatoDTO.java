package com.dota.partidas.dto;

import java.time.LocalDate;
import java.util.List;

import com.dota.partidas.model.Partida;

public class CampeonatoDTO {

    private Long id;
    private String nome;
    private String modo;
    private String patchCampeonato;
    private LocalDate comecoCamp;
    private LocalDate fimCamp;
    private List<Partida>partidas;

    public CampeonatoDTO() {

    }

    public CampeonatoDTO(Long id, LocalDate comecoCamp, LocalDate fimCamp, String modo, String nome, List<Partida> partidas, String patchCampeonato) {
        this.comecoCamp = comecoCamp;
        this.fimCamp = fimCamp;
        this.modo = modo;
        this.nome = nome;
        this.partidas = partidas;
        this.patchCampeonato = patchCampeonato;
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

    public LocalDate getComecoCamp() {
        return comecoCamp;
    }

    public void setComecoCamp(LocalDate comecoCamp) {
        this.comecoCamp = comecoCamp;
    }

    public LocalDate getFimCamp() {
        return fimCamp;
    }

    public void setFimCamp(LocalDate fimCamp) {
        this.fimCamp = fimCamp;
    }

    public List<Partida> getPartidas() {
        return partidas;
    }

    public void setPartidas(List<Partida> partidas) {
        this.partidas = partidas;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    
    
}
