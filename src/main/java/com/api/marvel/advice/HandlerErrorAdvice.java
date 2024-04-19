package com.api.marvel.advice;

import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class HandlerErrorAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<Map<String, Object>> handleAccessDenied(Exception exception) {

        if (exception instanceof AccessDeniedException) {
            Map<String, Object> response = new HashMap<>();
            response.put("Message", exception.getMessage());
            response.put("status", HttpStatus.FORBIDDEN.value());

            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        } else if(exception instanceof JWTVerificationException){
            Map<String, Object> response = new HashMap<>();
            response.put("Message", exception.getMessage());
            response.put("status", HttpStatus.FORBIDDEN.value());

            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("Message", exception.getMessage());
            response.put("status", HttpStatus.FORBIDDEN.value());

            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        }
    }
}
