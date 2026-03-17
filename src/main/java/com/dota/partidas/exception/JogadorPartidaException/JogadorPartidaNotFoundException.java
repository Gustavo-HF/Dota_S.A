package com.dota.partidas.exception.JogadorPartidaException;

import com.dota.partidas.exception.RequestException;

public class JogadorPartidaNotFoundException extends RequestException {

     public JogadorPartidaNotFoundException(String messageErro) {
        super( "PlayerMatchNotFound", messageErro);
    }
    
}
