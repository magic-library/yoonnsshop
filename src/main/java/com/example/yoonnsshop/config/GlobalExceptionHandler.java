package com.example.yoonnsshop.config;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseMessageDto> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(false, errors);
        return ResponseEntity.badRequest().body(responseMessageDto);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseMessageDto> handleIllegalArgumentException(IllegalArgumentException ex) {
        ResponseMessageDto responseMessageDto = new ResponseMessageDto(false, ex.getMessage());
        return ResponseEntity.badRequest().body(responseMessageDto);
    }
}

