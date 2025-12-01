package com.dota.partidas.model;

import java.time.LocalTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Partida {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)

    private Long id;

    @Min(value=0, message="O valor mínimo da diferença de patrimônio das duas equipes não pode ser menor que zero")
    private double diferencaPatrimonioEquipes;

    @NotBlank(message="O campo data não pode estar vazio")
    private String data;

    @Min(value=2, message="O valor mínimo presente no placar de uma partida deve ser de 2 pontos")
    private int pontuacao;

    @NotBlank(message="O campo de picks não pode estar vazio")
    private String picks;

    @NotBlank(message="O campo de bans não pode estar vazio")
    private String bans;

    @Column(unique=true)
    @NotBlank(message="O campo de mvp não pode estar vazio")
    private String mvp;
    
    @NotNull(message="O tempo de duração da partida não pode estar nulo")
    private LocalTime duracaoPartida;

    @OneToMany(mappedBy = "partida")
    private List<JogadorPartida> jogadores;

    @ManyToOne
    @JoinColumn(name="campeonato_id")
    private Campeonato campeonato;

    @ManyToOne
    @JoinColumn(name = "timeA_id")
    private Time timeA;

    @ManyToOne
    @JoinColumn(name = "timeB_id")
    private Time timeB;

    public Partida(){

    }

    public Partida(String bans, String data, double diferencaPatrimonioEquipes, LocalTime duracaoPartida, Long id, List<JogadorPartida> jogadores, String mvp, String patrimonioLiquidoIndividual, String picks, int pontuacao, Campeonato campeonato, Time timeA, Time timeB) {
        this.bans = bans;
        this.data = data;
        this.timeA = timeA;
        this.timeB = timeB;
        this.campeonato = campeonato;
        this.diferencaPatrimonioEquipes = diferencaPatrimonioEquipes;
        this.duracaoPartida = duracaoPartida;
        this.id = id;
        this.jogadores = jogadores;
        this.mvp = mvp;
        this.picks = picks;
        this.pontuacao = pontuacao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getDiferencaPatrimonioEquipes() {
        return diferencaPatrimonioEquipes;
    }

    public void setDiferencaPatrimonioEquipes(double diferencaPatrimonioEquipes) {
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

    public LocalTime getDuracaoPartida() {
        return duracaoPartida;
    }

    public void setDuracaoPartida(LocalTime duracaoPartida) {
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

    public Time getTimeA() {
        return timeA;
    }

    public void setTimeA(Time timeA) {
        this.timeA = timeA;
    }

    public Time getTimeB() {
        return timeB;
    }

    public void setTimeB(Time timeB) {
        this.timeB = timeB;
    }





}
