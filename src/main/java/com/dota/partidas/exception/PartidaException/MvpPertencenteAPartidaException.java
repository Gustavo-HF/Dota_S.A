package com.dota.partidas.exception.PartidaException;

import com.dota.partidas.exception.RequestException;

public class MvpPertencenteAPartidaException extends RequestException {

    public MvpPertencenteAPartidaException(String MessageErro){

        super("ToBeMvpAPlayerMustHaveParticipatedInTheMatch", MessageErro);

    }
    
}
