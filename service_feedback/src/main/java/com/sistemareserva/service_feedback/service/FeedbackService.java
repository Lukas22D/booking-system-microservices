package com.sistemareserva.service_feedback.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.sistemareserva.service_feedback.client.repository.FeedbackRepository;
import com.sistemareserva.service_feedback.model.Feedback;

import jakarta.transaction.Transactional;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import com.sistemareserva.service_feedback.client.broker.Producer;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import lombok.AllArgsConstructor;


@AllArgsConstructor
@Service
public class FeedbackService {
    
    private final FeedbackRepository feedbackRepository;
    private final Producer producer;
    private final Logger logger = LoggerFactory.getLogger(FeedbackService.class);

    @Async
    public CompletableFuture<Feedback> saveFeedback(Feedback feedback) {
        return CompletableFuture.supplyAsync(() -> {
            calculateAverageRating(feedback.getIdQuarto());
            return feedbackRepository.save(feedback);
        });
    }

    public Feedback getFeedbackById(Long id) {
        return feedbackRepository.findById(id).orElseThrow(
            () -> new RuntimeException("Feedback not found with id " + id)
        );
    }

    public void deleteFeedback(Long id) {
        feedbackRepository.findById(id)
            .map(feedback -> {
                feedbackRepository.delete(feedback);
                return null;
            })
            .orElseThrow(
                () -> new RuntimeException("Feedback not found with id " + id)
            );
    }

    @Transactional
    public void deleteFeedbackByQuarto(Long idQuarto) {
            feedbackRepository.deleteByIdQuarto(idQuarto);
    }

    public List<Feedback> getFeedbackByQuarto(Long idQuarto) {
        return feedbackRepository.findByIdQuarto(idQuarto).orElseThrow(
            () -> new RuntimeException("Feedback not found with idQuarto " + idQuarto)
        );
    }

    public Feedback updateFeedback(Long id, Feedback feedback) {
       return feedbackRepository.findById(id)
            .map(feedbackObj -> {
                feedbackObj.setFeedback(feedback.getFeedback());
                feedbackObj.setRating(feedback.getRating());
                feedbackObj.setIdQuarto(feedback.getIdQuarto());
                feedbackObj.setIdUser(feedback.getIdUser());
                return feedbackRepository.save(feedbackObj);
            })
            .orElseThrow(
                () -> new RuntimeException("Feedback not found with id " + id)
            );
    }


    private Void calculateAverageRating (Long idQuarto) {
        List<BigDecimal > ratings = feedbackRepository.findRatingsByIdQuarto(idQuarto);
        BigDecimal averageRating = BigDecimal.valueOf(ratings.stream().mapToDouble(BigDecimal::doubleValue).average().orElse(0.0));
        logger.info("Average rating for quarto " + idQuarto + " is " + averageRating);
        producer.sendUpdateRating(idQuarto, averageRating);
        return null;
    }
}
