package com.dota.partidas.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.ElementCollection;
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
import jakarta.validation.constraints.Size;

@Entity
public class Partida {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)

    private Long id;

    @Min(value=0, message="O valor mínimo da diferença de patrimônio das duas equipes não pode ser menor que zero")
    private Double diferencaPatrimonioEquipes;

    @NotNull(message="O campo data não pode estar vazio")
    private LocalDate data;

    @NotBlank(message="O campo pontuação não pode estar vazio")
    private String pontuacao;

    @ElementCollection
    @Size(min = 10, max = 10, message = "Picks devem conter exatamente 10 heróis")    
    private List<String> picks;

    @Size(min = 10, max = 10, message = "Bans devem conter exatamente 10 heróis")
    private List<String> bans;

    @NotNull(message="O campo mvp não pode estar vazio")
    @ManyToOne
    @JoinColumn(name = "mvp_id")
    private Jogador mvp;
    
    @NotNull(message="O tempo de duração da partida não pode estar nulo")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime duracaoPartida;

    @OneToMany(mappedBy = "partida")
    private List<JogadorPartida> jogadores;

    @ManyToOne
    @JoinColumn(name="campeonato_id")
    private Campeonato campeonato;

    @NotNull(message="A partida deve ter o time A definido")
    @ManyToOne
    @JoinColumn(name = "timeA_id")
    private Time timeA;

    @NotNull(message="A partida deve ter o time B definido")
    @ManyToOne
    @JoinColumn(name = "timeB_id")
    private Time timeB;

    public Partida(){

    }

    public Partida(List<String> bans, LocalDate data, Double diferencaPatrimonioEquipes, LocalTime duracaoPartida, Long id, List<JogadorPartida> jogadores, Jogador mvp,  List<String> picks, String pontuacao, Campeonato campeonato, Time timeA, Time timeB) {
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

    public Double getDiferencaPatrimonioEquipes() {
        return diferencaPatrimonioEquipes;
    }

    public void setDiferencaPatrimonioEquipes(Double diferencaPatrimonioEquipes) {
        this.diferencaPatrimonioEquipes = diferencaPatrimonioEquipes;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(String pontuacao) {
        this.pontuacao = pontuacao;
    }

    public List<String> getPicks() {
        return picks;
    }

    public void setPicks(List<String> picks) {
        this.picks = picks;
    }

    public List<String> getBans() {
        return bans;
    }

    public void setBans(List<String> bans) {
        this.bans = bans;
    }

    public Jogador getMvp() {
        return mvp;
    }

    public void setMvp(Jogador mvp) {
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
