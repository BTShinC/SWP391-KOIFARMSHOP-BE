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

    @GetMapping("/all")
    public ResponseEntity<List<TransactionReponse>> getAllTransactions() {
        List<TransactionReponse> transactions = transactionService.getAllTransactions();
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }
    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<TransactionReponse>> getTransactionsByAccountId(@PathVariable String accountId) {
        List<TransactionReponse> transactions = transactionService.getTransactionsByAccountId(accountId);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    // API cập nhật trạng thái giao dịch
    @PutMapping("/updateStatus/{transactionId}")
    public ResponseEntity<TransactionReponse> updateTransactionStatus(@PathVariable String transactionId, @RequestParam String status) {
        TransactionReponse updatedTransaction = transactionService.updateTransactionStatus(transactionId, status);
        return new ResponseEntity<>(updatedTransaction, HttpStatus.OK);
    }
}
