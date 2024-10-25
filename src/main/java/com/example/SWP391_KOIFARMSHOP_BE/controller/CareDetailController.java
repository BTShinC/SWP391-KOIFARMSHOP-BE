package com.example.SWP391_KOIFARMSHOP_BE.controller;

import com.example.SWP391_KOIFARMSHOP_BE.model.CareDetailRequest;
import com.example.SWP391_KOIFARMSHOP_BE.model.CareDetailResponse;
import com.example.SWP391_KOIFARMSHOP_BE.service.CareDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/care-details")
@CrossOrigin("*")
public class CareDetailController {

    @Autowired
    private CareDetailService careDetailService;

    // Create a new CareDetail
    @PostMapping
    public ResponseEntity<CareDetailResponse> createCareDetail(@RequestBody CareDetailRequest request) {
        CareDetailResponse response = careDetailService.createCareDetail(request);
        return ResponseEntity.ok(response);
    }

    // Get CareDetails by ConsignmentID
    @GetMapping("/consignment/{consignmentID}")
    public ResponseEntity<List<CareDetailResponse>> getCareDetailsByConsignmentId(@PathVariable String consignmentID) {
        List<CareDetailResponse> responses = careDetailService.getCareDetailsByConsignmentId(consignmentID);
        return ResponseEntity.ok(responses);
    }
}
