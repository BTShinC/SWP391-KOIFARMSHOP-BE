package com.example.SWP391_KOIFARMSHOP_BE.model;

import lombok.Data;

@Data
public class ShopCartResponse {

    private String shopCartID;
    private String breed;
    private double price;
    private int quantity;
    private String type;  // "Product" hoặc "ProductCombo"
    private String accountId;
    private String productId;
}
