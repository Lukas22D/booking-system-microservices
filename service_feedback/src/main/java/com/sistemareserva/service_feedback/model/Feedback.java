package com.sistemareserva.service_feedback.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

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

    
    @Column(precision = 2, scale = 1)
    private BigDecimal  rating;

    private Long idQuarto;

    private Long idUser;

    public Feedback(String feedback, BigDecimal  rating, Long idQuarto, Long idUser) {
        this.feedback = feedback;
        this.rating = rating;
        this.idQuarto = idQuarto;
        this.idUser = idUser;
    }
}   

