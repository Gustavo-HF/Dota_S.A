package com.dota.partidas.exception.TimeException;

import com.dota.partidas.exception.RequestException;

public class CampeaoDoTIException extends RequestException {

    public CampeaoDoTIException (String MessageErro){

        super("ThereCanOnlyBeOneTIChampion", MessageErro);

    }
    
}
