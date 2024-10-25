package com.example.SWP391_KOIFARMSHOP_BE.controller;

import com.example.SWP391_KOIFARMSHOP_BE.pojo.TotalRevenue;
import com.example.SWP391_KOIFARMSHOP_BE.service.TotalRevenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/revenue")
@CrossOrigin("*")
public class TotalRevenueController {

    @Autowired
    private TotalRevenueService totalRevenueService;

    // Endpoint để tính tổng doanh thu theo khoảng thời gian
    @GetMapping("/calculate")
    public ResponseEntity<TotalRevenue> calculateTotalRevenue(
            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {

        TotalRevenue revenue = totalRevenueService.calculateTotalRevenue(startDate, endDate);
        return ResponseEntity.ok(revenue);
    }
}
