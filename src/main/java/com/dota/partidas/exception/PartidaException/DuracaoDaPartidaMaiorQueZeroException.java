package com.dota.partidas.exception.PartidaException;

import com.dota.partidas.exception.RequestException;

public class DuracaoDaPartidaMaiorQueZeroException extends RequestException{

    public DuracaoDaPartidaMaiorQueZeroException(String MessageErro){

        super("MatchDurationLessThanZero", MessageErro);

    }
    
}
