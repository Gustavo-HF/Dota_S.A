package com.dota.partidas.exception.PartidaException;

import com.dota.partidas.exception.RequestException;

public class PartidaDeveEstarVinculadaAUmCampeonatoException extends RequestException {

    public PartidaDeveEstarVinculadaAUmCampeonatoException(String MessageErro){

        super("MatchMustBeLinkedToAChampionship", MessageErro);

    }
    
}
