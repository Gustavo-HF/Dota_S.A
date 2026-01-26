package com.dota.partidas.exception.PartidaException;

import com.dota.partidas.exception.RequestException;

public class QuantidadeDePicksPorPartidaIgualADezException extends RequestException {

    public QuantidadeDePicksPorPartidaIgualADezException(String MessageErro){

        super("TheNumberOfHeroesMustBeEqualToTen", MessageErro);

    }
    
}
