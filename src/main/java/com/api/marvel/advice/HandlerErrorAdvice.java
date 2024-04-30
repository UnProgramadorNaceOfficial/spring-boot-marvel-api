package com.api.marvel.advice;

import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class HandlerErrorAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleException(Exception exception){

        if(exception instanceof AccessDeniedException){
            Map<String, Object> response = new HashMap<>();
            response.put("Message", exception.getMessage());
            response.put("status", HttpStatus.FORBIDDEN.value());
            response.put("prueba", "prueba excepcion");

            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);

        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("Message", exception.getMessage());
            response.put("status", HttpStatus.UNAUTHORIZED.value());
            response.put("prueba2", "prueba excepcion");

            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
    }

    @ExceptionHandler(JWTVerificationException.class)
    public ResponseEntity<Map<String, Object>> handleTokenException(JWTVerificationException exception){
        Map<String, Object> response = new HashMap<>();
        response.put("Message", exception.getMessage());
        response.put("status", HttpStatus.UNAUTHORIZED.value());
        response.put("detail", "The token is invalid");

        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }
}
