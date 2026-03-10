package com.dota.partidas.dto;

import java.time.LocalDate;
import java.util.List;

import com.dota.partidas.model.Partida;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class CampeonatoDTO {

    
    private Long id;

    @NotBlank(message="O campo de nome do campeonato deve estar preenchido")
    private String nome;

    @NotBlank(message="O modo do campeonato não pode estar vazio e deve ser informado no formato md3 ou md5")
    @Pattern(regexp="md3|md5", message="O modo deve ser md3 ou md5")
    private String modo;

    @NotBlank(message="O patch em que o campeonato é jogado deve ser informado")
    @Pattern(regexp="\\d\\.\\d{2}[a-z]", message="O patch deve estar no formato X.XXa, X.XXb, etc.")
    private String patchCampeonato;

    @NotNull(message="Data de começo de campeonato deve estar preenchida")
    private LocalDate comecoCamp;

    @NotNull(message="Data de fim de campeonato deve estar preenchida")
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
