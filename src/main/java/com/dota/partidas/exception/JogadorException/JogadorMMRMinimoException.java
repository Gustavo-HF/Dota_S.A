package com.dota.partidas.exception.JogadorException;

import com.dota.partidas.exception.RequestException;

public class JogadorMMRMinimoException extends RequestException {

    public JogadorMMRMinimoException(String MessageErro){

        super("PlayerMMRMinimum6000", MessageErro);

    }
    
}
