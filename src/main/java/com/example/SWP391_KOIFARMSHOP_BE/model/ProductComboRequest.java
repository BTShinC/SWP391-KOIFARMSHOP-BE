package com.example.SWP391_KOIFARMSHOP_BE.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data

public class ProductComboRequest {

    @NotBlank(message = "Name cannot be blank")
    private String comboName;
    @Positive(message = "Size must be a positive number")
    private float size;
    @NotBlank(message = "Breed cannot be blank")
    private String breed;
    @NotBlank(message = "Health status cannot be blank")
    private String healthStatus;
    @PositiveOrZero(message = "Quantity must be zero or a positive number")
    private int quantity;
    @NotBlank(message = "Description cannot be blank")
    private String description;
    @NotBlank(message = "Image URL cannot be blank")
    private String image;
    @Positive(message = "Price must be a positive number")
    private double price;
    @NotBlank(message = "Consignment type cannot be blank")
    private String consignmentType;
    @Positive(message = "Desired price must be a positive number")
    private double desiredPrice;
    @NotBlank(message = "Type cannot be blank")
    private String type;

}