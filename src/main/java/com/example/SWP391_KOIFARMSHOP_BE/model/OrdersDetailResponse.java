package com.example.SWP391_KOIFARMSHOP_BE.model;

import lombok.Data;

@Data
public class OrdersDetailResponse {
    private String ordersDetailID;
    private String productID;
    private String productName;
    private double productPrice;
    private double discountedPrice;
    private String type;
    private String productComboID;
    private String comboName;
    private double comboPrice;
}
