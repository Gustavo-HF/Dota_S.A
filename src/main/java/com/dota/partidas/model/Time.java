package com.dota.partidas.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Time {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)

    private Long id;

    @NotBlank(message="O campo nome não pode estar vazio")
    private String nome;

    @NotBlank(message="Cada time deve ter uma região de " 
    + "origem cadastrada, por isso este campo não pode ficar vazio")
    private String regiao;

    @NotNull(message="Este campo não pode estar vazio")
    @Column(unique=true)
    private Integer classificacaoMundial;

    @Min(value = 5, message = "O time deve ter no mínimo 5 jogadores")
    @Max(value = 10, message = "O time deve ter no máximo 10 jogadores")
    private Integer numeroJogadores = 0;

    private Boolean isUltimoCampeaoDoTi;

    @NotNull(message="Este campo não pode estar vazio")
    @Column(unique=true)
    private Integer classificacaoCampeonato;

    @OneToMany(mappedBy="time")
    private List<Jogador> jogadores;
    public Time(){

    }

    public Time(Integer classificacaoCampeonato, Integer classificacaoMundial, Long id, Boolean isUltimoCampeaoDoTi, String nome, Integer numeroJogadores, String regiao, List<Jogador> jogadores) {
        this.jogadores = jogadores;
        this.classificacaoCampeonato = classificacaoCampeonato;
        this.classificacaoMundial = classificacaoMundial;
        this.id = id;
        this.isUltimoCampeaoDoTi = isUltimoCampeaoDoTi;
        this.nome = nome;
        this.numeroJogadores = numeroJogadores;
        this.regiao = regiao;
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

    public String getRegiao() {
        return regiao;
    }

    public void setRegiao(String regiao) {
        this.regiao = regiao;
    }

    public Integer getClassificacaoMundial() {
        return classificacaoMundial;
    }

    public void setClassificacaoMundial(Integer classificacaoMundial) {
        this.classificacaoMundial = classificacaoMundial;
    }

    public Integer getNumeroJogadores() {
        return numeroJogadores;
    }

    public void setNumeroJogadores(Integer numeroJogadores) {
        this.numeroJogadores = numeroJogadores;
    }

    public Boolean isIsUltimoCampeaoDoTi() {
        return isUltimoCampeaoDoTi;
    }

    public void setIsUltimoCampeaoDoTi(Boolean isUltimoCampeaoDoTi) {
        this.isUltimoCampeaoDoTi = isUltimoCampeaoDoTi;
    }

    public Integer getClassificacaoCampeonato() {
        return classificacaoCampeonato;
    }

    public void setClassificacaoCampeonato(Integer classificacaoCampeonato) {
        this.classificacaoCampeonato = classificacaoCampeonato;
    }

    public List<Jogador> getJogadores() {
        return jogadores;
    }

    public void setJogadores(List<Jogador> jogadores) {
        this.jogadores = jogadores;
    }


}
