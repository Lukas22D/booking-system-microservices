package com.sistemareserva.service_feedback.service;

import org.springframework.stereotype.Service;
import com.sistemareserva.service_feedback.client.repository.FeedbackRepository;
import com.sistemareserva.service_feedback.model.Feedback;

import java.util.List;

import lombok.AllArgsConstructor;


@AllArgsConstructor
@Service
public class FeedbackService {
    
    private final FeedbackRepository feedbackRepository;


    public Feedback saveFeedback(Feedback feedback) {
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
}
