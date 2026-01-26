package com.dota.partidas.exception.PartidaException;

import com.dota.partidas.exception.RequestException;

public class TimeBDeveTer5JogadoresException extends RequestException{
    
    public TimeBDeveTer5JogadoresException(String MessageErro){

        super("TeamBMustHave5Players.", MessageErro);

    }
}
