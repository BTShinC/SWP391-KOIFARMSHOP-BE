package com.example.SWP391_KOIFARMSHOP_BE.controller;

import com.example.SWP391_KOIFARMSHOP_BE.pojo.Payment;
import com.example.SWP391_KOIFARMSHOP_BE.service.IPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
@CrossOrigin
@RequestMapping("/api/payment")
public class PaymentController {
    @Autowired
    private IPaymentService iPaymentService;
    //HTTP Verb GET, POST, PUT, DELETE (API)
    @GetMapping("/")
    public ResponseEntity<List<Payment>> fetchALlPayment(){
        return ResponseEntity.ok(iPaymentService.getAllPayment());
    }
    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public Payment savePayment(@RequestBody Payment payment){
        return iPaymentService.insertPayment(payment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Payment> updatePayment(@PathVariable long id, @RequestBody Payment payment){
        Payment updatePayment = iPaymentService.updatePayment(id, payment);
        return ResponseEntity.ok(updatePayment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePayment(@PathVariable long id){
        iPaymentService.deletePayment(id);
        return ResponseEntity.ok("Delete Payment success!");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Payment>> getPaymentByID(@PathVariable long id){
        Optional<Payment> payment = iPaymentService.getPaymentByID(id);
        return  ResponseEntity.ok(payment);
    }
}
