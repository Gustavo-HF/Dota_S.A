package com.dota.partidas.exception;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(RequestException.class)
    public ResponseEntity<?> handle(RequestException ex) {
        return ResponseEntity.badRequest().body(Map.of("error", ex.getErroCode(), "message", ex.getMessage()));
    }

    @ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationErrors(org.springframework.web.bind.MethodArgumentNotValidException ex) {
        // Aqui pegamos todos os erros de todos os campos de uma vez
        var erros = ex.getFieldErrors().stream()
                .map(error -> Map.of(
                "campo", error.getField(),
                "mensagem", error.getDefaultMessage()
        ))
                .toList();

        return ResponseEntity.badRequest().body(erros);
    }

}
