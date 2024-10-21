package com.example.SWP391_KOIFARMSHOP_BE.controller;

import com.example.SWP391_KOIFARMSHOP_BE.model.AccountResponse;
import com.example.SWP391_KOIFARMSHOP_BE.model.ConsignmentResponse;
import com.example.SWP391_KOIFARMSHOP_BE.service.RefundService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/refund")
public class RefundController {

    @Autowired
    private RefundService refundService;

    @PostMapping("/{consignmentID}")
    public String refundProduct(@PathVariable String consignmentID) {
           String accountResponse = refundService.refundProduct(consignmentID);
            return accountResponse; // Trả về mã 200 OK cùng với AccountResponse
    }
}
