package com.example.SWP391_KOIFARMSHOP_BE.model;

import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
public class OrderResponse {
    private String orderID;
    private String status;
    private double total;
    private double discountedTotal;
    private Date date;
    private String description;
    private String accountID;
    private String feedbackId;
    private List<OrdersDetailResponse> ordersDetails;
}
