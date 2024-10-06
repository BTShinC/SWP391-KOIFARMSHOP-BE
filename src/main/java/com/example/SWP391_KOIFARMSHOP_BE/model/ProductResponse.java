package com.example.SWP391_KOIFARMSHOP_BE.model;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class ProductResponse {
    private String productID;
    private String productName;
    private String breed;
    private float size;
    private String sex;
    private String healthStatus;
    private String personalityTrait;
    private String origin;
    private String description;
    private String image;
    private double price;
    private String certificate;
    private String type;
    private int quantity;
    private String status;
    private double desiredPrice;
    private String consignmentType;

}
