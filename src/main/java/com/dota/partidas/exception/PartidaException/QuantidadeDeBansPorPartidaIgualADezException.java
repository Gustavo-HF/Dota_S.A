package com.dota.partidas.exception.PartidaException;

import com.dota.partidas.exception.RequestException;

public class QuantidadeDeBansPorPartidaIgualADezException extends RequestException {

    public QuantidadeDeBansPorPartidaIgualADezException(String MessageErro){

        super("TheNumberOfBansPerMatchMustBeEqualToTen", MessageErro );
    }
    
}
