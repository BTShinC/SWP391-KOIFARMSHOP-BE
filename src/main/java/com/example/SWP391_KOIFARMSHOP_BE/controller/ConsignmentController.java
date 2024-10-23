package com.example.SWP391_KOIFARMSHOP_BE.controller;

import com.example.SWP391_KOIFARMSHOP_BE.model.ConsignmentRequest;
import com.example.SWP391_KOIFARMSHOP_BE.model.ConsignmentResponse;
import com.example.SWP391_KOIFARMSHOP_BE.service.ConsignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/consignments")
@CrossOrigin("*")
public class ConsignmentController {

    @Autowired
    private ConsignmentService consignmentService;

    @PostMapping
    public ResponseEntity<ConsignmentResponse> createConsignment(@RequestBody ConsignmentRequest consignmentRequest) {
        ConsignmentResponse newConsignment = consignmentService.createConsignment(consignmentRequest);
        return ResponseEntity.ok(newConsignment);
    }

    @GetMapping
    public ResponseEntity<List<ConsignmentResponse>> getAllConsignments() {
        List<ConsignmentResponse> consignments = consignmentService.getAllConsignments();
        return ResponseEntity.ok(consignments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsignmentResponse> getConsignmentById(@PathVariable String id) {
        ConsignmentResponse consignment = consignmentService.getConsignmentById(id);
        return ResponseEntity.ok(consignment);
    }
    @GetMapping("/account/{accountID}")
    public ResponseEntity<List<ConsignmentResponse>> getConsignmentsByAccountID(@PathVariable String accountID) {
        List<ConsignmentResponse> consignments = consignmentService.getConsignmentsByAccountID(accountID);
        return ResponseEntity.ok(consignments);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ConsignmentResponse> updateConsignment(@PathVariable String id, @RequestBody ConsignmentRequest consignmentRequest) {
        ConsignmentResponse updatedConsignment = consignmentService.updateConsignment(id, consignmentRequest);
        return ResponseEntity.ok(updatedConsignment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConsignment(@PathVariable String id) {
        consignmentService.deleteConsignment(id);
        return ResponseEntity.noContent().build();
    }
}
