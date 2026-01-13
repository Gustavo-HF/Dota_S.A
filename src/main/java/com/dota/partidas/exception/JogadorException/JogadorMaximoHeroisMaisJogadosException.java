package com.dota.partidas.exception.JogadorException;

import com.dota.partidas.exception.RequestException;

public class JogadorMaximoHeroisMaisJogadosException extends RequestException {

    public JogadorMaximoHeroisMaisJogadosException (String MessageErro){

        super("TopPlayerMostPlayedHeroes", MessageErro);

    }
    
}
