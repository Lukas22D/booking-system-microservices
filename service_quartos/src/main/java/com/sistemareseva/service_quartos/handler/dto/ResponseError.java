package com.sistemareseva.service_quartos.handler.dto;

/**
 * ResponseError
 */
public record ResponseError(
    String message,
    Integer status
) {
}