package com.dota.partidas.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Entity
public class Campeonato {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)

    private Long id;

    @NotBlank(message="O campo de nome do campeonato não pode estar vazio")
    private String nome;

    @NotBlank(message="O campo de modo do campeonato não pode estar vazio e deve ser informado no formato md3 ou md5")
    @Pattern(regexp="md3|md5", message="O modo deve ser md3 ou md5")
    private String modo;

    @NotBlank(message="O patch em que o campeonato é jogado deve ser informado")
    @Pattern(regexp="\\d\\.\\d{2}[a-z]", message="O patch deve estar no formato X.XXa, X.XXb, etc.")
    private String patchCampeonato;

    @NotNull(message="O campo da data de inicio do campeonato não pode estar vazia")
    private LocalDate comecoCamp;

    @NotNull(message="O campo de data do fim do campeonato não pode estar vazio")
    private LocalDate fimCamp;

    @OneToMany(mappedBy="campeonato")
    private List<Partida>partidas;

    public Campeonato(){

    }

    public Campeonato(LocalDate comecoCamp, LocalDate fimCamp, Long id, String modo, String nome, List<Partida> partidas, String patchCampeonato) {
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


}
