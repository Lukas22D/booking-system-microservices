package com.sistemareserva.service_feedback.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "tb_feedback")
public class Feedback {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String feedback;

    @DecimalMin(value = "0.00", message = "O rating deve ser no mínimo 0.00")
    @DecimalMax(value = "5.00", message = "O rating deve ser no máximo 5.00")
    @Digits(integer = 1, fraction = 2, message = "O rating deve ter no máximo 1 dígito inteiro e 2 casas decimais")
    private Double rating;

    private Long idQuarto;

    private Long idUser;

    public Feedback(String feedback, Double rating, Long idQuarto, Long idUser) {
        this.feedback = feedback;
        this.rating = rating;
        this.idQuarto = idQuarto;
        this.idUser = idUser;
    }
}   

