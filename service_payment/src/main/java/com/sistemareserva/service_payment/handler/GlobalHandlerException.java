package com.sistemareserva.service_payment.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.sistemareserva.service_payment.handler.dto.GlobalResponseError;
import com.sistemareserva.service_payment.handler.error.OrderCreateException;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class GlobalHandlerException {
    

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<GlobalResponseError> handleEntityNotFoundException(EntityNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new GlobalResponseError(e.getMessage(), "Entity not found"));
    }

    @ExceptionHandler(OrderCreateException.class)
    public ResponseEntity<GlobalResponseError> handleOrderCreateException(OrderCreateException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new GlobalResponseError(e.getMessage(), "Error creating order"));
    }
}
