package com.example.SWP391_KOIFARMSHOP_BE.model;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class PaymentRequest {
    @PositiveOrZero(message = "Sale price must be zero or a positive number")
    private double salePrice;
    @PositiveOrZero(message = "Ship price must be zero or a positive number")
    private double shipPrice;
    @PositiveOrZero(message = "Subtotal must be zero or a positive number")
    private double subTotal;
}
