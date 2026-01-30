 package com.dota.partidas.exception.TimeException;

import com.dota.partidas.exception.RequestException;


 public class ClassificacaoMundialExistenteException extends RequestException{

    public ClassificacaoMundialExistenteException(String MessageErro){

        super("ExistingWorldRanking", MessageErro);

    }
 }