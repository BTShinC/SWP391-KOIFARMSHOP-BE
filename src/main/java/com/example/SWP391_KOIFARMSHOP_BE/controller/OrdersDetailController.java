package com.example.SWP391_KOIFARMSHOP_BE.controller;

import com.example.SWP391_KOIFARMSHOP_BE.model.OrdersDetailResponse;
import com.example.SWP391_KOIFARMSHOP_BE.service.OrdersDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders-details")
public class OrdersDetailController {

    @Autowired
    private OrdersDetailService ordersDetailService;

    // Lấy tất cả OrdersDetails
    @GetMapping
    public ResponseEntity<List<OrdersDetailResponse>> getAllOrdersDetails() {
        List<OrdersDetailResponse> responses = ordersDetailService.getAllOrdersDetails();
        return ResponseEntity.ok(responses);
    }

    // Lấy tất cả OrdersDetails theo OrderID
    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<OrdersDetailResponse>> getOrdersDetailsByOrderId(@PathVariable String orderId) {
        List<OrdersDetailResponse> responses = ordersDetailService.getOrdersDetailsByOrderId(orderId);
        return ResponseEntity.ok(responses);
    }
}
