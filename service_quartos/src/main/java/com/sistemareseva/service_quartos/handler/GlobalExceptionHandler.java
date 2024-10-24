package com.sistemareseva.service_quartos.handler;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.sistemareseva.service_quartos.handler.dto.ResponseError;

@RestControllerAdvice
public class GlobalExceptionHandler {
    

    @ExceptionHandler(RuntimeException.class)
    public ResponseError handleRuntimeException(RuntimeException e) {
        return new ResponseError(e.getMessage(), 400);
    }
}
