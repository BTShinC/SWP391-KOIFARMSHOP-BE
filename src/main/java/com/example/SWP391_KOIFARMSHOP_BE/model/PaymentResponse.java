package com.example.SWP391_KOIFARMSHOP_BE.model;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class PaymentResponse {
    private String paymentID;
    private double salePrice;
    private double shipPrice;
    private double subTotal;
}
