package com.dota.partidas.exception.TimeException;

import com.dota.partidas.exception.RequestException;

public class TimeDevePossuir5a10JogadoresException extends RequestException {

    public TimeDevePossuir5a10JogadoresException (String MessageErro){

        super("TheTeamMustHave5To10Players.", MessageErro);

    }
    
}
