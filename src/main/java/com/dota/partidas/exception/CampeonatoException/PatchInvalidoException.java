package com.dota.partidas.exception.CampeonatoException;

import com.dota.partidas.exception.RequestException;

public class PatchInvalidoException extends RequestException {

    public PatchInvalidoException(String messageErro){  
        super("PatchFormatInvalid", messageErro);
    }
    
}
