package com.dota.partidas.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Jogador {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    
    private Long id;
    private String nome;
    private String nickname;
    private int posicao;
    private String nacionalidade;
    private String funcao;
    private String heroisMaisJogados;
    private int mmr;
    

    public Jogador(){

    }

    @ManyToOne
    @JoinColumn(name="time_id")
    private Time time;

    @OneToMany(mappedBy = "jogador")
    private List<JogadorPartida> partidas;
    

    public Jogador(String funcao, String heroisMaisJogados, Long id, int mmr, String nacionalidade, String nickname, String nome, int posicao, Time time, List<JogadorPartida> partidas) {
        this.funcao = funcao;
        this.partidas = partidas;
        this.heroisMaisJogados = heroisMaisJogados;
        this.id = id;
        this.mmr = mmr;
        this.nacionalidade = nacionalidade;
        this.nickname = nickname;
        this.nome = nome;
        this.posicao = posicao;
        this.time = time;
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

    public int getPosicao() {
        return posicao;
    }

    public void setPosicao(int posicao) {
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

    public String getHeroisMaisJogados() {
        return heroisMaisJogados;
    }

    public void setHeroisMaisJogados(String heroisMaisJogados) {
        this.heroisMaisJogados = heroisMaisJogados;
    }

    public int getMmr() {
        return mmr;
    }

    public void setMmr(int mmr) {
        this.mmr = mmr;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public List<JogadorPartida> getPartidas() {
        return partidas;
    }

    public void setPartidas(List<JogadorPartida> partidas) {
        this.partidas = partidas;
    }


}

