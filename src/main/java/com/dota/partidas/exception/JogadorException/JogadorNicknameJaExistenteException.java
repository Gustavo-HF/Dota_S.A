package com.dota.partidas.exception.JogadorException;

import com.dota.partidas.exception.RequestException;

public class JogadorNicknameJaExistenteException extends RequestException {

    public JogadorNicknameJaExistenteException (String MessageErro){

        super("NicknameAlreadyExists", MessageErro);

    }
    
}
