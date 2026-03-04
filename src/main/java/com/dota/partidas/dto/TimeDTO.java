package com.dota.partidas.dto;

import java.util.List;

import com.dota.partidas.model.Jogador;



public class TimeDTO {
    
    private Long id;
    private String nome;
    private String regiao;
    private Integer classificacaoMundial;
    private Integer numeroJogadores = 0;
    private Boolean isUltimoCampeaoDoTi;
    private Integer classificacaoCampeonato;
    private List<Jogador> jogadores;

    public TimeDTO() {
    }

    public TimeDTO(Integer classificacaoCampeonato, Integer classificacaoMundial, Long id, Boolean isUltimoCampeaoDoTi, List<Jogador> jogadores, String nome, String regiao) {
        this.classificacaoCampeonato = classificacaoCampeonato;
        this.classificacaoMundial = classificacaoMundial;
        this.id = id;
        this.isUltimoCampeaoDoTi = isUltimoCampeaoDoTi;
        this.jogadores = jogadores;
        this.nome = nome;
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

    public Boolean getIsUltimoCampeaoDoTi() {
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
