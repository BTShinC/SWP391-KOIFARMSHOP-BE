package com.example.SWP391_KOIFARMSHOP_BE.model;

import lombok.Data;

import java.util.Date;
@Data
public class OrderResponse {
    private String orderID;
    private String status;
    private double total;
    private Date date;
    private String description;
    private String accountId;
    private long feedbackId;
//    private Set<OrdersDetailResponse> ordersDetail;
}
