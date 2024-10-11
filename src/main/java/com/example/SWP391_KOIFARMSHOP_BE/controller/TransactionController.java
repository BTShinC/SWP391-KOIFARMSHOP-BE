package com.example.SWP391_KOIFARMSHOP_BE.controller;

import com.example.SWP391_KOIFARMSHOP_BE.model.FeedbackResponse;
import com.example.SWP391_KOIFARMSHOP_BE.model.TransactionReponse;
import com.example.SWP391_KOIFARMSHOP_BE.model.TransactionRequest;
import com.example.SWP391_KOIFARMSHOP_BE.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@CrossOrigin("*")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    // API để tạo mới một transaction
    @PostMapping("/create")
    public ResponseEntity<TransactionReponse> createTransaction(@RequestBody TransactionRequest transactionRequest) {
        TransactionReponse transactionResponse = transactionService.create(transactionRequest);
        return new ResponseEntity<>(transactionResponse, HttpStatus.CREATED);
    }
//    @GetMapping("/all")
//    public ResponseEntity<List<TransactionReponse>> getAllFeedback() {
//        List<TransactionReponse> feedbackResponses = transactionService.getAllFeedback();
//        return ResponseEntity.ok(feedbackResponses); // Trả về status 200 cùng với danh sách feedback
//    }

}
