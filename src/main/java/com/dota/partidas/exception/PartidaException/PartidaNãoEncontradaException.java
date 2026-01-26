package com.dota.partidas.exception.PartidaException;

import com.dota.partidas.exception.RequestException;

public class PartidaNãoEncontradaException extends RequestException {
    
    public PartidaNãoEncontradaException(String MessageErro){

        super("MatchNotFound", MessageErro);

    }
}
