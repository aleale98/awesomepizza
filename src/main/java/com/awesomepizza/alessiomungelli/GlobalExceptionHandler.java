package com.awesomepizza.alessiomungelli;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeException(RuntimeException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", ex.getMessage());

        // If the message contains "not found" or "wrong id" return 404
        // 400 otherway (Bad Request)
        HttpStatus status = HttpStatus.BAD_REQUEST;
        if (ex.getMessage().toLowerCase().contains("not found") || ex.getMessage().contains("Wrong id")) {
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(errorResponse, status);
    }
}
