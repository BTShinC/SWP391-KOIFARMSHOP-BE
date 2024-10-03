package com.example.SWP391_KOIFARMSHOP_BE.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class ProductComboResponse {
    private long productComboID;

    private float size;

    private String breed;

    private String healthStatus;

    private int quantity;

    private String desciption;

    private String image;

    private double price;

    private String consignmentType;

    private double desiredPrice;

    private String type;
}
