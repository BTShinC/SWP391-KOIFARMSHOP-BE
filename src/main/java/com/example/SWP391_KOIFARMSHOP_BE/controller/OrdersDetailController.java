package com.example.SWP391_KOIFARMSHOP_BE.controller;

import com.example.SWP391_KOIFARMSHOP_BE.model.OrdersDetailResponse;
import com.example.SWP391_KOIFARMSHOP_BE.model.OrdersDetailSummaryResponse;
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
    @GetMapping("/order/{orderID}")
    public ResponseEntity<List<OrdersDetailResponse>> getOrdersDetailsByOrderId(@PathVariable String orderID) {
        List<OrdersDetailResponse> responses = ordersDetailService.getOrdersDetailsByOrderId(orderID);
        return ResponseEntity.ok(responses);
    }
    // Lấy tất cả OrdersDetails theo loại "Trang trại" với count và tổng doanh thu
    @GetMapping("/farm")
    public ResponseEntity<OrdersDetailSummaryResponse> getOrdersDetailsByTypeFarm() {
        OrdersDetailSummaryResponse response = ordersDetailService.getOrdersDetailsByTypeFarm();
        return ResponseEntity.ok(response);
    }

    // Lấy tất cả OrdersDetails theo loại "Ký gửi" với count và tổng doanh thu
    @GetMapping("/consignment")
    public ResponseEntity<OrdersDetailSummaryResponse> getOrdersDetailsByTypeConsignment() {
        OrdersDetailSummaryResponse response = ordersDetailService.getOrdersDetailsByTypeConsignment();
        return ResponseEntity.ok(response);
    }

}
