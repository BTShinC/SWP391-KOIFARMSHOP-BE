package com.example.SWP391_KOIFARMSHOP_BE.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class ProductComboResponse {
    private String productComboID;
    private String comboName;
    private float size;
    private String breed;
    private String healthStatus;
    private int quantity;
    private String description;
    private String image;
    private String image1;
    private String image2;
    private double price;
    private String consignmentType;
    private double desiredPrice;
    private String type;
    private String status;
    private String carePackageID;
}