package com.example.SWP391_KOIFARMSHOP_BE.controller;

import com.example.SWP391_KOIFARMSHOP_BE.pojo.Account;
import com.example.SWP391_KOIFARMSHOP_BE.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/account")
public class AccountController {
    @Autowired
    private IAccountService iAccountService;
    //HTTP Verb GET, POST, PUT, DELETE (API)
    @GetMapping("/")
    public ResponseEntity<List<Account>> fetchALlAccount(){
        return ResponseEntity.ok(iAccountService.getAllAccount());
    }
//    @PostMapping("/")
//    @ResponseStatus (HttpStatus.CREATED)
//    public Account saveAccount(@org.springframework.web.bind.annotation.RequestBody Account account){
//        return iAccountService.insertAccount(account);
//    }

    @PutMapping("/{id}")
    public ResponseEntity<Account> updateAccount(@PathVariable long id, @RequestBody Account account){
        Account updateAccount = iAccountService.updateAccount(id, account);
        return ResponseEntity.ok(updateAccount);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable long id){
        iAccountService.deleteAccount(id);
        return ResponseEntity.ok("Delete account success!");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Account>> getAccountByID(@PathVariable long id){
        Optional<Account> account = iAccountService.getAccountByID(id);
        return  ResponseEntity.ok(account);
    }
}

