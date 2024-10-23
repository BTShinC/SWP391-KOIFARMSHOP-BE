package com.example.SWP391_KOIFARMSHOP_BE.model;

import lombok.Data;

@Data
public class OrdersDetailRequest {
    private String productID;
    private String productComboID;
    private String orderID;
}