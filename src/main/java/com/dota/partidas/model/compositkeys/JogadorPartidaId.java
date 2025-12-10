package com.dota.partidas.model.compositkeys;

import java.io.Serializable;

import jakarta.persistence.Embeddable;

@Embeddable
public class JogadorPartidaId implements Serializable{

    private Long jogadorId;
    
    private Long partidaId;

    public JogadorPartidaId(){
        
    }

    public JogadorPartidaId(Long jogadorId, Long partidaId) {
        this.jogadorId = jogadorId;
        this.partidaId = partidaId;
    }

    public Long getJogadorId() {
        return jogadorId;
    }

    public void setJogadorId(Long jogadorId) {
        this.jogadorId = jogadorId;
    }

    public Long getPartidaId() {
        return partidaId;
    }

    public void setPartidaId(Long partidaId) {
        this.partidaId = partidaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JogadorPartidaId)) return false;
        JogadorPartidaId that = (JogadorPartidaId) o;
        return java.util.Objects.equals(jogadorId, that.jogadorId) &&
            java.util.Objects.equals(partidaId, that.partidaId);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(jogadorId, partidaId);
    }   

    
}
