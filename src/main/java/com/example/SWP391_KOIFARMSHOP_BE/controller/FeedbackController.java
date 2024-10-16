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

    @PostMapping
    public ResponseEntity<FeedbackResponse> createFeedback(@RequestBody FeedbackRequest feedbackRequest) {
        FeedbackResponse feedbackResponse = feedbackService.createFeedback(feedbackRequest);
        return ResponseEntity.ok(feedbackResponse);
    }

    @GetMapping("/all")
    public ResponseEntity<List<FeedbackResponse>> getAllFeedback() {
        List<FeedbackResponse> feedbackResponses = feedbackService.getAllFeedback();
        return ResponseEntity.ok(feedbackResponses); // Trả về status 200 cùng với danh sách feedback
    }

    @GetMapping("/order/{orderID}")
    public ResponseEntity<FeedbackResponse> getFeedbackByOrderID(@PathVariable String orderID) {
        FeedbackResponse feedbackResponse = feedbackService.getFeedbackByOrderID(orderID);
        return ResponseEntity.ok(feedbackResponse);
    }
}
