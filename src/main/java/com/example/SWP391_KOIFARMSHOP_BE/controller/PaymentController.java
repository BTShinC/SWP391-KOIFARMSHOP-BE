package com.example.SWP391_KOIFARMSHOP_BE.controller;

import com.example.SWP391_KOIFARMSHOP_BE.model.PaymentRequest;
import com.example.SWP391_KOIFARMSHOP_BE.model.PaymentResponse;
import com.example.SWP391_KOIFARMSHOP_BE.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    // API để lấy tất cả payments
    @GetMapping
    public ResponseEntity<List<PaymentResponse>> fetchAllPayments() {
        List<PaymentResponse> payments = paymentService.getAllPayments();
        return ResponseEntity.ok(payments);
    }

    // API để tạo payment mới
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentResponse savePayment(@Valid @RequestBody PaymentRequest paymentRequest) {
        return paymentService.createPayment(paymentRequest);
    }

    // API để cập nhật payment
    @PutMapping("/{id}")
    public ResponseEntity<PaymentResponse> updatePayment(@PathVariable String id, @Valid @RequestBody PaymentRequest paymentRequest) {
        PaymentResponse updatedPayment = paymentService.updatePayment(id, paymentRequest);
        return ResponseEntity.ok(updatedPayment);
    }

    // API để xóa payment
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePayment(@PathVariable String id) {
        paymentService.deletePayment(id);
        return ResponseEntity.ok("Delete payment success!");
    }

    // API để lấy payment theo ID
    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponse> getPaymentByID(@PathVariable String id) {
        PaymentResponse payment = paymentService.getPaymentByID(id);
        return ResponseEntity.ok(payment);
    }
}
