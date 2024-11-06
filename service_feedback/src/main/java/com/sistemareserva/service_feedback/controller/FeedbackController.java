package com.sistemareserva.service_feedback.controller;

import java.util.concurrent.CompletableFuture;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistemareserva.service_feedback.controller.dto.FeedbackRequest;
import com.sistemareserva.service_feedback.model.Feedback;
import com.sistemareserva.service_feedback.service.FeedbackService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;

    @PostMapping
    public CompletableFuture<ResponseEntity<Feedback>> createFeedback(@RequestBody FeedbackRequest feedback) {
        return feedbackService.saveFeedback(feedback.toModel())
                .thenApply(savedFeedback -> ResponseEntity.ok().body(savedFeedback));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFeedback(Long id) {
        feedbackService.deleteFeedback(id);
        return ResponseEntity.ok().build();
    }

}
