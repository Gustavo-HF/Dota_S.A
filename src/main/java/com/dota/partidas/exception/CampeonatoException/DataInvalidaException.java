package com.dota.partidas.exception.CampeonatoException;

import com.dota.partidas.exception.RequestException;

public class DataInvalidaException extends RequestException{
    
    public DataInvalidaException(String messageErro){
        super("DataNotFound", messageErro);
    }
}
