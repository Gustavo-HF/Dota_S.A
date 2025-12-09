package com.dota.partidas.exception;

 
public class RequestException extends RuntimeException{
    private final String erroCode;

    

    public RequestException(String erroCode, String message){
        super(message);
        this.erroCode = erroCode;
    }

    public String getErroCode() {
        return erroCode;
    }

    
    
}
