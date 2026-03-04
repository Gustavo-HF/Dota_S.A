package com.dota.partidas.dto;

import java.util.List;

public class JogadorDTO {

    private Long id;
    private String nome;
    private String nickname;
    private Integer posicao;
    private String nacionalidade;
    private String funcao;
    private List<String> heroisMaisJogados;
    private Integer mmr;
    
    
    public JogadorDTO() {

    }

    public JogadorDTO(String funcao, List<String> heroisMaisJogados, Long id, Integer mmr, String nacionalidade, String nickname, String nome, Integer posicao) {
        this.funcao = funcao;
        this.heroisMaisJogados = heroisMaisJogados;
        this.id = id;
        this.mmr = mmr;
        this.nacionalidade = nacionalidade;
        this.nickname = nickname;
        this.nome = nome;
        this.posicao = posicao;
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getPosicao() {
        return posicao;
    }

    public void setPosicao(Integer posicao) {
        this.posicao = posicao;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public List<String> getHeroisMaisJogados() {
        return heroisMaisJogados;
    }

    public void setHeroisMaisJogados(List<String> heroisMaisJogados) {
        this.heroisMaisJogados = heroisMaisJogados;
    }

    public Integer getMmr() {
        return mmr;
    }

    public void setMmr(Integer mmr) {
        this.mmr = mmr;
    }


    
}

