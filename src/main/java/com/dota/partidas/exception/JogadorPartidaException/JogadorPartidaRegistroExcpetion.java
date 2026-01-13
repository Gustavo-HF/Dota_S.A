package com.dota.partidas.exception.JogadorPartidaException;

import com.dota.partidas.exception.RequestException;

public class JogadorPartidaRegistroExcpetion extends RequestException {

    public JogadorPartidaRegistroExcpetion (String MessageErro){

        super("PlayerMatchRegistrationNotFound", MessageErro);

    }
    
}
