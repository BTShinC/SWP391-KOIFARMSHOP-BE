package com.example.SWP391_KOIFARMSHOP_BE.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ShopCartRequest {

    @NotBlank(message = "Account ID cannot be blank")
    private String accountId;

    @NotBlank(message = "Product or Product Combo ID cannot be blank")
    private String productId;  // Có thể là productId hoặc productComboId
}
