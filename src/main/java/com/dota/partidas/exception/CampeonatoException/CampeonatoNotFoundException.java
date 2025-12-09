package com.dota.partidas.exception.CampeonatoException;

import com.dota.partidas.exception.RequestException;

public class CampeonatoNotFoundException extends RequestException{

    public CampeonatoNotFoundException(String messageErro){
        super("CampeonatoNotFound", messageErro);
    }
}
