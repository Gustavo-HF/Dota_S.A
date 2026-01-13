package com.dota.partidas.exception.JogadorPartidaException;

import com.dota.partidas.exception.RequestException;

public class JogadorDevePertencerAPartidaException extends RequestException{

    public JogadorDevePertencerAPartidaException (String MessageErro){
        super("PlayerMustBePartOfTheMatch", MessageErro);
    }
    
}
