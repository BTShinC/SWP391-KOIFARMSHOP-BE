package com.example.SWP391_KOIFARMSHOP_BE.controller;

import com.example.SWP391_KOIFARMSHOP_BE.pojo.SaleTransaction;
import com.example.SWP391_KOIFARMSHOP_BE.service.ISaleTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
@CrossOrigin
@RequestMapping("/api/saleTransaction")
public class SaleTransactionController {
    @Autowired
    private ISaleTransactionService iSaleTransactionService;
    @GetMapping("/")
    public ResponseEntity<List<SaleTransaction>> fetchALlSaleTransaction(){
        return ResponseEntity.ok(iSaleTransactionService.getAllSaleTransaction());
    }
    @PostMapping("/")
    @ResponseStatus (HttpStatus.CREATED)
    public SaleTransaction saveSaleTransaction(@RequestBody SaleTransaction saleTransaction){
        return iSaleTransactionService.insertSaleTransaction(saleTransaction);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SaleTransaction> updateSaleTransaction(@PathVariable long id, @RequestBody SaleTransaction saleTransaction){
        SaleTransaction updateSaleTransaction = iSaleTransactionService.updateSaleTransaction(id, saleTransaction);
        return ResponseEntity.ok(updateSaleTransaction);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSaleTransaction(@PathVariable long id){
        iSaleTransactionService.deleteSaleTransaction(id);
        return ResponseEntity.ok("Delete Sale Transaction success!");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<SaleTransaction>> getSaleTransactionByID(@PathVariable long id){
        Optional<SaleTransaction> saleTransaction = iSaleTransactionService.getSaleTransactionByID(id);
        return  ResponseEntity.ok(saleTransaction);
    }
}
