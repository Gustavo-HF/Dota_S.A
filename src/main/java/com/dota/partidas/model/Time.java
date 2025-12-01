package com.dota.partidas.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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

    @Column(unique=true)
    private int classificacaoMundial;

    @NotNull(message="O número de jogadores não pode ser nulo")
    private int numeroJogadores;

    private boolean isUltimoCampeaoDoTi;

    @Column(unique=true)
    private int classificacaoCampeonato;

    @OneToMany(mappedBy="jogador")
    private List<Jogador> jogadores;
    public Time(){

    }

    public Time(int classificacaoCampeonato, int classificacaoMundial, Long id, boolean isUltimoCampeaoDoTi, String nome, int numeroJogadores, String regiao, List<Jogador> jogadores) {
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

    public int getClassificacaoMundial() {
        return classificacaoMundial;
    }

    public void setClassificacaoMundial(int classificacaoMundial) {
        this.classificacaoMundial = classificacaoMundial;
    }

    public int getNumeroJogadores() {
        return numeroJogadores;
    }

    public void setNumeroJogadores(int numeroJogadores) {
        this.numeroJogadores = numeroJogadores;
    }

    public boolean isIsUltimoCampeaoDoTi() {
        return isUltimoCampeaoDoTi;
    }

    public void setIsUltimoCampeaoDoTi(boolean isUltimoCampeaoDoTi) {
        this.isUltimoCampeaoDoTi = isUltimoCampeaoDoTi;
    }

    public int getClassificacaoCampeonato() {
        return classificacaoCampeonato;
    }

    public void setClassificacaoCampeonato(int classificacaoCampeonato) {
        this.classificacaoCampeonato = classificacaoCampeonato;
    }

    public List<Jogador> getJogadores() {
        return jogadores;
    }

    public void setJogadores(List<Jogador> jogadores) {
        this.jogadores = jogadores;
    }


}
