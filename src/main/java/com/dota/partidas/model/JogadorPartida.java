package com.dota.partidas.model;

import com.dota.partidas.model.compositkeys.JogadorPartidaId;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

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

    @NotBlank(message="O valor do KDA deve estar informado")
    private String kda;

    @Min(value=0, message="O valor do patrimônio líquido não pode ser menor que zero")
    private Integer patrimonioLiquidoIndividual;

    public JogadorPartida(){

    }

    public JogadorPartida(Jogador jogador, String kda, Integer patrimonioLiquidoIndividual, Partida partida) {
        this.jogador = jogador;
        this.patrimonioLiquidoIndividual = patrimonioLiquidoIndividual;
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

    public Integer getPatrimonioLiquidoIndividual() {
        return patrimonioLiquidoIndividual;
    }

    public void setPatrimonioLiquidoIndividual(Integer patrimonioLiquidoIndividual) {
        this.patrimonioLiquidoIndividual = patrimonioLiquidoIndividual;
    }




    
}
