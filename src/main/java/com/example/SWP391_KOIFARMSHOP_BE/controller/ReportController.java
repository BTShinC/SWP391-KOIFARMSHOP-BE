package com.example.SWP391_KOIFARMSHOP_BE.controller;

import com.example.SWP391_KOIFARMSHOP_BE.model.OrderReport;
import com.example.SWP391_KOIFARMSHOP_BE.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/report")
public class ReportController {
    @Autowired
    private ReportService reportService;

    @GetMapping("/orders")
    public ResponseEntity<List<OrderReport>> getOrders() {
        List<OrderReport> orderReports = reportService.ordershop();
        return new ResponseEntity<>(orderReports, HttpStatus.OK);
    }
    @GetMapping("/countProduct")
    public String getCountProduct() {
        return reportService.getcountproduct();
    }
    @GetMapping("/countProductCombo")
    public String getCountProductCombo() {
        return reportService.getcountproductCombo();
    }
    @GetMapping("/countallproduct")
    public String getallProduct() {
        return reportService.getall();
    }
    @GetMapping("/accountcustomer")
    public String getaccountcustomer(){
        return reportService.getaccountcustomer();
    }
    @GetMapping("/accountall")
    public String getaccount(){
        return reportService.getallaccount();
    }
    @GetMapping("/top5-breeds")
    public List<String> getTop5BestSellingBreeds() {
        return reportService.getTop5BestSellingBreeds();
    }

}
