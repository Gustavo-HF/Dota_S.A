package com.dota.partidas.exception.JogadorPartidaException;

import com.dota.partidas.exception.RequestException;

public class JogadorFormatoKDAExcpetion extends RequestException {

    public JogadorFormatoKDAExcpetion (String MessageErro){
        super("PlayerFormatKDANotCompatible", MessageErro);
    }
    
}
