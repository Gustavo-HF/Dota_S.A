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
public class Partida {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)

    private Long id;
    private String patrimonioLiquido;
    private String diferencaPatrimonioEquipes;
    private String data;
    private int pontuacao;
    private String picks;
    private String bans;
    private String mvp;
    private byte duracaoPartida;

    @OneToMany(mappedBy = "partida")
    private List<JogadorPartida> jogadores;

    @ManyToOne
    @JoinColumn(name="campeonato_id")
    private Campeonato campeonato;

    public Partida(){

    }

    public Partida(String bans, String data, String diferencaPatrimonioEquipes, byte duracaoPartida, Long id, List<JogadorPartida> jogadores, String mvp, String patrimonioLiquido, String picks, int pontuacao, Campeonato campeonato) {
        this.bans = bans;
        this.data = data;
        this.campeonato = campeonato;
        this.diferencaPatrimonioEquipes = diferencaPatrimonioEquipes;
        this.duracaoPartida = duracaoPartida;
        this.id = id;
        this.jogadores = jogadores;
        this.mvp = mvp;
        this.patrimonioLiquido = patrimonioLiquido;
        this.picks = picks;
        this.pontuacao = pontuacao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPatrimonioLiquido() {
        return patrimonioLiquido;
    }

    public void setPatrimonioLiquido(String patrimonioLiquido) {
        this.patrimonioLiquido = patrimonioLiquido;
    }

    public String getDiferencaPatrimonioEquipes() {
        return diferencaPatrimonioEquipes;
    }

    public void setDiferencaPatrimonioEquipes(String diferencaPatrimonioEquipes) {
        this.diferencaPatrimonioEquipes = diferencaPatrimonioEquipes;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(int pontuacao) {
        this.pontuacao = pontuacao;
    }

    public String getPicks() {
        return picks;
    }

    public void setPicks(String picks) {
        this.picks = picks;
    }

    public String getBans() {
        return bans;
    }

    public void setBans(String bans) {
        this.bans = bans;
    }

    public String getMvp() {
        return mvp;
    }

    public void setMvp(String mvp) {
        this.mvp = mvp;
    }

    public byte getDuracaoPartida() {
        return duracaoPartida;
    }

    public void setDuracaoPartida(byte duracaoPartida) {
        this.duracaoPartida = duracaoPartida;
    }

    public List<JogadorPartida> getJogadores() {
        return jogadores;
    }

    public void setJogadores(List<JogadorPartida> jogadores) {
        this.jogadores = jogadores;
    }

    public Campeonato getCampeonato() {
        return campeonato;
    }

    public void setCampeonato(Campeonato campeonato) {
        this.campeonato = campeonato;
    }





}
