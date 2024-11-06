package com.sistemareserva.service_feedback.client.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sistemareserva.service_feedback.model.Feedback;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    
    Optional<List<Feedback>> findByIdQuarto(Long idQuarto);

    @Query("SELECT f.rating FROM tb_feedback f WHERE f.idQuarto = :idQuarto")
    List<BigDecimal> findRatingsByIdQuarto(@Param("idQuarto") Long idQuarto);
}
