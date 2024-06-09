package com.example.yoonnsshop.config;

import com.example.yoonnsshop.common.ApiResponse;
import com.example.yoonnsshop.common.exception.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        ApiResponse apiResponse = new ApiResponse(false, errors);
        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        ApiResponse apiResponse = new ApiResponse(false, ex.getMessage());
        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(DatabaseInsertionException.class)
    public ResponseEntity<ApiResponse> handleDatabaseInsertionException(DatabaseInsertionException ex) {
        ApiResponse apiResponse = new ApiResponse(false, ex.getMessage());
        return ResponseEntity.internalServerError().body(apiResponse);
    }

    @ExceptionHandler(DatabaseUpdateException.class)
    public ResponseEntity<ApiResponse> handleDatabaseUpdateException(DatabaseUpdateException ex) {
        ApiResponse apiResponse = new ApiResponse(false, ex.getMessage());
        return ResponseEntity.internalServerError().body(apiResponse);
    }

    @ExceptionHandler(DatabaseDeletionException.class)
    public ResponseEntity<ApiResponse> handleDatabaseDeletionException(DatabaseDeletionException ex) {
        ApiResponse apiResponse = new ApiResponse(false, ex.getMessage());
        return ResponseEntity.internalServerError().body(apiResponse);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ApiResponse> handleInvalidRequestException(InvalidRequestException ex) {
        ApiResponse apiResponse = new ApiResponse(false, ex.getMessage());
        return ResponseEntity.badRequest().body(apiResponse);
    }
}

