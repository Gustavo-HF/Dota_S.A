package com.dota.partidas.exception;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(RequestException.class)
    public ResponseEntity<?> handle(RequestException ex){
        return ResponseEntity.badRequest().body(Map.of("error", ex.getErroCode(), "message", ex.getMessage()));
    }
    
}
