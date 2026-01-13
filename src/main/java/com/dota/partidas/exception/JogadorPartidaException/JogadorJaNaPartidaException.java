package com.dota.partidas.exception.JogadorPartidaException;

import com.dota.partidas.exception.RequestException;

public class JogadorJaNaPartidaException extends RequestException {

    public JogadorJaNaPartidaException(String messageErro) {
        super( "PlayerAlreadyInTheMatch", messageErro);
    }
    
}
