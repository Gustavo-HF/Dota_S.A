package com.dota.partidas.exception.JogadorException;

import com.dota.partidas.exception.RequestException;

public class JogadorNotFoundException extends RequestException {

    public JogadorNotFoundException (String MessageErro){

        super("JogadorNotFound", MessageErro);

    }
    
}
