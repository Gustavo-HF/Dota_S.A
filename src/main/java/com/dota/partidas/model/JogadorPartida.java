package com.dota.partidas.model;

import com.dota.partidas.model.compositkeys.JogadorPartidaId;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name="jogador_partida")
public class JogadorPartida {
    
    @EmbeddedId
    private JogadorPartidaId jogadorPartidaId = new JogadorPartidaId();

    @ManyToOne
    @MapsId("jogadorId")
    @JoinColumn(name="jogador_id")
    private Jogador jogador;

    @ManyToOne
    @MapsId("partidaId")
    @JoinColumn(name="partida_id")
    private Partida partida;

    private String kda;

    public JogadorPartida(){

    }

    public JogadorPartida(Jogador jogador, String kda, Partida partida) {
        this.jogador = jogador;
        this.kda = kda;
        this.partida = partida;
    }

    public JogadorPartidaId getJogadorPartidaId() {
        return jogadorPartidaId;
    }

    public void setJogadorPartidaId(JogadorPartidaId jogadorPartidaId) {
        this.jogadorPartidaId = jogadorPartidaId;
    }

    public Jogador getJogador() {
        return jogador;
    }

    public void setJogador(Jogador jogador) {
        this.jogador = jogador;
    }

    public Partida getPartida() {
        return partida;
    }

    public void setPartida(Partida partida) {
        this.partida = partida;
    }

    public String getKda() {
        return kda;
    }

    public void setKda(String kda) {
        this.kda = kda;
    }




    
}
