package com.sistemareserva.service_payment.infra.gateways.payment.paypal.dto;

import java.util.*;

import com.sistemareserva.service_payment.client.feignClient.dto.ReservaResponse;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

/**
 * OrderRequest
 */
@Getter
@Setter
public class OrderRequest {

    private final String intent = "CAPTURE";
    private List<Map<String, ?>> purchase_units;

    public OrderRequest(List<ReservaResponse> reservas) {
        BigDecimal total = reservas.stream()
            .map(reserva -> new BigDecimal(reserva.valorTotal()))
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        this.purchase_units = List.of(new HashMap<>() {{
            put("amount", new Amount(total.toString()));
            put("items", reservas.stream()
                .map(reserva -> new Item("Reserva de quarto: " + reserva.idQuarto(), reserva.quantidadeDias(), reserva.UnidadeDiaria()))
                .toList());
        }});
        
    }

    // Record para a Amount, incluindo breakdown com item_total correto
    private record Amount(String currency_code, String value, Breakdown breakdown) {
        public Amount(String value) {
            this("BRL", value, new Breakdown(value));
        }
    }

    // Record para o Breakdown, incluindo o item_total como um objeto
    private record Breakdown(UnitAmount item_total) {
        public Breakdown(String value) {
            this(new UnitAmount("BRL", value));
        }
    }

    // Record para o Item
    private record Item(String name, String quantity, UnitAmount unit_amount) {
        public Item(String name, String quantity, String value) {
            this(name, quantity, new UnitAmount(value));
        }
    }

    // Record para o UnitAmount
    private record UnitAmount(String currency_code, String value) {
        public UnitAmount(String value) {
            this("BRL", value);
        }
    }
}
