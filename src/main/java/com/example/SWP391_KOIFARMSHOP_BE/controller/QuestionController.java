package com.example.SWP391_KOIFARMSHOP_BE.controller;

import com.example.SWP391_KOIFARMSHOP_BE.model.QuestionReponse;
import com.example.SWP391_KOIFARMSHOP_BE.model.QuestionRequest;
import com.example.SWP391_KOIFARMSHOP_BE.model.TransactionReponse;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.Question;
import com.example.SWP391_KOIFARMSHOP_BE.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/question")
@CrossOrigin("*")
public class QuestionController {


    @Autowired
    QuestionService questionService;


    @PostMapping("/create")
    public ResponseEntity<Question> createQuestion(@RequestBody QuestionRequest questionRequest) {
        Question savedQuestion = questionService.create(questionRequest);
        return new ResponseEntity<>(savedQuestion, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<QuestionReponse>> getAllTransactions() {
        List<QuestionReponse> question = questionService.getAllQuestion();
        return new ResponseEntity<>(question, HttpStatus.OK);
    }
    @PutMapping("/updateStatus/{id}")
    public ResponseEntity<?> updateQuestionStatus(
            @RequestParam("ID") String questionID,
            @RequestParam("status") String newStatus) {
            QuestionReponse updatedQuestionResponse = questionService.updateStatus(questionID, newStatus);
            return ResponseEntity.ok(updatedQuestionResponse);

    }

}
