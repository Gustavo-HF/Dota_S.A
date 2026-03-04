package com.dota.partidas.dto;

import com.dota.partidas.model.Jogador;
import com.dota.partidas.model.Partida;

public class JogadorPartidaDTO {

    private Jogador jogador;
    private Partida partida;
    private String kda;
    private Integer patrimonioLiquidoIndividual;

    public JogadorPartidaDTO() {
    }

    public JogadorPartidaDTO(Jogador jogador, String kda, Partida partida, Integer patrimonioLiquidoIndividual) {
        this.jogador = jogador;
        this.kda = kda;
        this.partida = partida;
        this.patrimonioLiquidoIndividual = patrimonioLiquidoIndividual;
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
