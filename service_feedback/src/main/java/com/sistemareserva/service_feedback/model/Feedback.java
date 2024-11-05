package com.sistemareserva.service_feedback.model;

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

    private float rating;

    private Long idQuarto;

    private Long idUser;

    public Feedback(String feedback, float rating, Long idQuarto, Long idUser) {
        this.feedback = feedback;
        this.rating = rating;
        this.idQuarto = idQuarto;
        this.idUser = idUser;
    }
}   

