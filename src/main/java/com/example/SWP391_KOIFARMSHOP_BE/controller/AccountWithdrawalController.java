package com.example.SWP391_KOIFARMSHOP_BE.controller;

import com.example.SWP391_KOIFARMSHOP_BE.model.AccountWithdrawalReponse;
import com.example.SWP391_KOIFARMSHOP_BE.model.AccountWithdrawalRequest;
import com.example.SWP391_KOIFARMSHOP_BE.model.TransactionReponse;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.AccountWithdrawal;
import com.example.SWP391_KOIFARMSHOP_BE.service.AccountWithdrawalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/AccountWithdrawal")
public class AccountWithdrawalController {

    @Autowired
    AccountWithdrawalService accountWithdrawalService;

    @PostMapping("/create")
    public ResponseEntity<AccountWithdrawal> createWithdrawal(@RequestBody AccountWithdrawalRequest accountWithdrawalRequest) {
            AccountWithdrawal accountWithdrawal = accountWithdrawalService.create(accountWithdrawalRequest);
            return ResponseEntity.ok(accountWithdrawal);
    }

    @GetMapping("/all")
    public ResponseEntity<List<AccountWithdrawalReponse>> getAllTransactions() {
        List<AccountWithdrawalReponse> transactions = accountWithdrawalService.getAllAccountWithdrawal();
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }
}
