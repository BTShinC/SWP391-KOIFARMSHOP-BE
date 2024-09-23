package com.example.SWP391_KOIFARMSHOP_BE.controller;

import com.example.SWP391_KOIFARMSHOP_BE.pojo.Feedback;
import com.example.SWP391_KOIFARMSHOP_BE.service.IFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
@CrossOrigin
@RequestMapping("/api/feedback")
public class FeedbackController {
    @Autowired
    private IFeedbackService iFeedbackService;
    @GetMapping("/")
    public ResponseEntity<List<Feedback>> fetchALlFeedback(){
        return ResponseEntity.ok(iFeedbackService.getAllFeedback());
    }
    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public Feedback saveFeedback(@org.springframework.web.bind.annotation.RequestBody Feedback feedback){
        return iFeedbackService.insertFeedback(feedback);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Feedback> updateFeedback(@PathVariable long id, @RequestBody Feedback feedback){
        Feedback updateFeedback = iFeedbackService.updateFeedback(id, feedback);
        return ResponseEntity.ok(updateFeedback);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFeedback(@PathVariable long id){
        iFeedbackService.deleteFeedback(id);
        return ResponseEntity.ok("Delete Feedback success!");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Feedback>> getFeedbackByID(@PathVariable long id){
        Optional<Feedback> feedback = iFeedbackService.getFeedbackByID(id);
        return  ResponseEntity.ok(feedback);
    }
}
