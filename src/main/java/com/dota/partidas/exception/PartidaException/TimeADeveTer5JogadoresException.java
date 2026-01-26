package com.dota.partidas.exception.PartidaException;

import com.dota.partidas.exception.RequestException;

public class TimeADeveTer5JogadoresException extends RequestException {

    public TimeADeveTer5JogadoresException(String MessageErro){

        super("TeamAMustHave5Players.", MessageErro);

    }
    
}
