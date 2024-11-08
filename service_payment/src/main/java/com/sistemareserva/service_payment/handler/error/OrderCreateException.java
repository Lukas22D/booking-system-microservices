package com.sistemareserva.service_payment.handler.error;

public class OrderCreateException extends RuntimeException{
    
    public OrderCreateException(String message) {
        super(message);
    }
}
