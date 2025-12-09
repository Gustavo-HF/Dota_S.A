package com.dota.partidas.exception.CampeonatoException;

import com.dota.partidas.exception.RequestException;

public class CampeonatoPartidasException extends RequestException{

    public CampeonatoPartidasException(String messageErro){
        super("CampeonatoMatchsNotFound", messageErro);
    }
    
}
