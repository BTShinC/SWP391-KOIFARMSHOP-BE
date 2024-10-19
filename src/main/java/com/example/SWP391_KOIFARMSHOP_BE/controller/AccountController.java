package com.example.SWP391_KOIFARMSHOP_BE.controller;

import com.example.SWP391_KOIFARMSHOP_BE.model.AccountResponse;
import com.example.SWP391_KOIFARMSHOP_BE.model.ProductResponse;
import com.example.SWP391_KOIFARMSHOP_BE.model.RegisterRequest;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.Account;
import com.example.SWP391_KOIFARMSHOP_BE.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/account")
//@PreAuthorize("hasAnyRole('Customer', 'Admin')")
public class AccountController {
    @Autowired
    private AccountService accountService;
    // API để lấy tất cả accounts (tương tự getAllProducts)
//    @GetMapping
//    public ResponseEntity<List<AccountResponse>> getAllAccounts() {
//        List<AccountResponse> accounts = accountService.getAllAccount();
//        return ResponseEntity.ok(accounts);
//    }

    // API để lấy account theo ID (tương tự getProductById)

    @GetMapping("/{id}")
    //@PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<AccountResponse> getAccountById(@PathVariable String id) {
        AccountResponse account = accountService.getAccountByID(id);
        return ResponseEntity.ok(account);
    }

    // API để cập nhật account (tương tự updateProduct)
    @PutMapping("/{id}")
    public ResponseEntity<AccountResponse> updateAccount(@PathVariable String id, @Valid @RequestBody RegisterRequest registerRequest) {
        AccountResponse updatedAccount = accountService.updateAccount(id, registerRequest);
        return ResponseEntity.ok(updatedAccount);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable String id){
        accountService.deleteAccount(id);
        return ResponseEntity.ok("Delete account success!");
    }

    @PutMapping("/updateBalance/{accountID}")
    public ResponseEntity<AccountResponse> updateAccountBalance(@PathVariable String accountID, @RequestParam double amount) {
        AccountResponse updatedAccount = accountService.updateAccountBalance(accountID, amount);
        return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
    }

    @PutMapping("/deductBalance/{accountID}")
    public ResponseEntity<AccountResponse> deductAccountBalance(@PathVariable String accountID, @RequestParam double amount) {
        AccountResponse updatedAccount = accountService.deductAccountBalance(accountID, amount);
        return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
    }

}

