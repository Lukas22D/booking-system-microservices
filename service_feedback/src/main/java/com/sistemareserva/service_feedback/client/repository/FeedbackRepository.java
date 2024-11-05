package com.sistemareserva.service_feedback.client.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistemareserva.service_feedback.model.Feedback;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    
    Optional<List<Feedback>> findByIdQuarto(Long idQuarto);
}
