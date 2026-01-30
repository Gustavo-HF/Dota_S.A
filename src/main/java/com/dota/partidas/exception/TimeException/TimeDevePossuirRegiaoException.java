package com.dota.partidas.exception.TimeException;

import com.dota.partidas.exception.RequestException;

public class TimeDevePossuirRegiaoException extends RequestException {

    public TimeDevePossuirRegiaoException (String MessageErro){

        super("TeamMustHaveRegionRegistered", MessageErro);

    }
    
}
