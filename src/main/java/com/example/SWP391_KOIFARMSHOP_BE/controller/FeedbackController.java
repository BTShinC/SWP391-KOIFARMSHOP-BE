package com.example.SWP391_KOIFARMSHOP_BE.controller;

import com.example.SWP391_KOIFARMSHOP_BE.model.FeedbackRequest;
import com.example.SWP391_KOIFARMSHOP_BE.model.FeedbackResponse;
import com.example.SWP391_KOIFARMSHOP_BE.service.FeedbackService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    // API để lấy tất cả feedbacks
    @GetMapping
    public ResponseEntity<List<FeedbackResponse>> fetchAllFeedback() {
        List<FeedbackResponse> feedbacks = feedbackService.getAllFeedback();
        return ResponseEntity.ok(feedbacks);
    }

    // API để tạo feedback mới
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<FeedbackResponse> saveFeedback(@Valid @RequestBody FeedbackRequest feedbackRequest) {
        FeedbackResponse createdFeedback = feedbackService.createFeedback(feedbackRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFeedback);
    }

    // API để cập nhật feedback
    @PutMapping("/{id}")
    public ResponseEntity<FeedbackResponse> updateFeedback(@PathVariable String id, @Valid @RequestBody FeedbackRequest feedbackRequest) {
        FeedbackResponse updatedFeedback = feedbackService.updateFeedback(id, feedbackRequest);
        return ResponseEntity.ok(updatedFeedback);
    }

    // API để xóa feedback
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFeedback(@PathVariable String id) {
        feedbackService.deleteFeedback(id);
        return ResponseEntity.ok("Delete feedback success!");
    }

    // API để lấy feedback theo ID
    @GetMapping("/{id}")
    public ResponseEntity<FeedbackResponse> getFeedbackByID(@PathVariable String id) {
        FeedbackResponse feedback = feedbackService.getFeedbackByID(id);
        return ResponseEntity.ok(feedback);
    }
}
