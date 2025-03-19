package com.aieta.springboot_crud.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleValidationsErrors(MethodArgumentNotValidException ex) {
        ErrorResponse json = new ErrorResponse(
            HttpStatus.BAD_REQUEST.value(), 
            "Error de validación"
        );

        ex.getBindingResult().getAllErrors().forEach(error -> {
            String propertyPath = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            json.addValidationError(propertyPath, message);
        });

        return new ResponseEntity<>(json, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleEntityNotFound(ConstraintViolationException ex) {
        ErrorResponse json = new ErrorResponse(
            HttpStatus.BAD_REQUEST.value(), 
            "Error de validación"
        );

        ex.getConstraintViolations().forEach(violation -> {
            String propertyPath = getFieldName(violation);
            String message = violation.getMessage();
            json.addValidationError(propertyPath, message);
        });

        return new ResponseEntity<>(json, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleEntityNotFound(Exception ex) {
        ErrorResponse json = new ErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR.value(), 
            "Error del servidor"
        );

        json.addValidationError("error", ex.getClass().getName());
        json.addValidationError("details", ex.getMessage());

        return new ResponseEntity<>(json, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private String getFieldName(ConstraintViolation<?> violation) {
        String propertyPath = violation.getPropertyPath().toString();
        String[] parts = propertyPath.split("\\.");
        return parts.length > 0 ? parts[parts.length - 1] : propertyPath;
    }
}
