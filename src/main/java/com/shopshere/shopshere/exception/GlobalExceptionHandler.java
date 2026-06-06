package com.shopshere.shopshere.exception;

import com.shopshere.shopshere.exception.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleNotFound(ResourceNotFoundException ex){
        return ResponseEntity.status(404).body(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> validationError(){
        return ResponseEntity.badRequest().body("Validation failed");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> general(Exception ex){
        return ResponseEntity.status(500).body("Server Error");
    }
}