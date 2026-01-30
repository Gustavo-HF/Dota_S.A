package com.dota.partidas.exception.TimeException;


import com.dota.partidas.exception.RequestException;

public class TimeNãoEncontradoException extends RequestException{

    public TimeNãoEncontradoException(String MessageErro){

        super("TeamNotFound", MessageErro);

    }

}
