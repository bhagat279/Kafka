package com.order.orderservice.exception;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.FieldError;

import org.springframework.web.bind.MethodArgumentNotValidException;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(
            MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse>
    handleValidationException(
            MethodArgumentNotValidException ex){

        Map<String,String> errors =
                new HashMap<>();

        ex.getBindingResult()
                .getAllErrors()
                .forEach(error -> {

                    String field =
                            ((FieldError) error)
                                    .getField();

                    String message =
                            error.getDefaultMessage();

                    errors.put(
                            field,
                            message);
                });

        ErrorResponse response =
                ErrorResponse.builder()
                        .message(
                                "Validation Failed")
                        .status(400)
                        .timestamp(
                                LocalDateTime.now())
                        .errors(errors)
                        .build();

        return ResponseEntity
                .badRequest()
                .body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse>
    handleException(Exception ex){

        log.error(
                "Unexpected Exception",
                ex);

        ErrorResponse response =
                ErrorResponse.builder()
                        .message(
                                ex.getMessage())
                        .status(500)
                        .timestamp(
                                LocalDateTime.now())
                        .build();

        return ResponseEntity
                .status(
                        HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }
}