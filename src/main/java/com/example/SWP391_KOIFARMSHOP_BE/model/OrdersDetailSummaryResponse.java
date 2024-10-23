package com.example.SWP391_KOIFARMSHOP_BE.model;

import lombok.Data;

import java.util.List;

@Data
public class OrdersDetailSummaryResponse {
    private int count;  // Số lượng OrdersDetail
    private double totalRevenue;  // Tổng doanh thu
    private List<OrdersDetailResponse> details;  // Danh sách OrdersDetail
}
