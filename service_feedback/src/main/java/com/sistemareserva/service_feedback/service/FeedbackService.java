package com.sistemareserva.service_feedback.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.sistemareserva.service_feedback.client.repository.FeedbackRepository;
import com.sistemareserva.service_feedback.model.Feedback;
import com.sistemareserva.service_feedback.client.broker.Producer;

import java.util.List;

import lombok.AllArgsConstructor;


@AllArgsConstructor
@Service
public class FeedbackService {
    
    private final FeedbackRepository feedbackRepository;
    private final Producer producer;

    @Async
    public Feedback saveFeedback(Feedback feedback) {
        calculateAverageRating(feedback.getIdQuarto());
        return feedbackRepository.save(feedback);
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
        List<Double> ratings = feedbackRepository.findRatingsByIdQuarto(idQuarto);
        Double averageRating = ratings.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
        producer.sendUpdateRating(idQuarto, averageRating);
        return null;
    }
}
