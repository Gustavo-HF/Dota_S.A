package com.dota.partidas.exception.TimeException;

import com.dota.partidas.exception.RequestException;

public class ClassificacaoCampeonatoExistenteException extends RequestException {

    public ClassificacaoCampeonatoExistenteException(String MessageErro){

        super("ClassificationInTheExistingChampionship", MessageErro);

    }
    
}
