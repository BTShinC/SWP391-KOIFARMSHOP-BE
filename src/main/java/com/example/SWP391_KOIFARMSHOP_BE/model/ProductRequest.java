package com.example.SWP391_KOIFARMSHOP_BE.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class ProductRequest {
    @NotBlank(message = "Product name cannot be blank")
    private String productName;

    @NotBlank(message = "Breed cannot be blank")
    private String breed;

    @Positive(message = "Size must be a positive number")
    private float size;

    @NotBlank(message = "Sex cannot be blank")
    private String sex;

    @NotBlank(message = "Health status cannot be blank")
    private String healthStatus;

    @NotBlank(message = "Personality trait cannot be blank")
    private String personalityTrait;

    @NotBlank(message = "Origin cannot be blank")
    private String origin;

    @NotBlank(message = "Description cannot be blank")

    private String description;


    @NotBlank(message = "Image URL cannot be blank")
    private String image;

    @Positive(message = "Price must be a positive number")
    private double price;

    @NotBlank(message = "Certificate cannot be blank")
    private String certificate;

    @NotBlank(message = "Type cannot be blank")
    private String type;

    @PositiveOrZero(message = "Quantity must be zero or a positive number")
    private int quantity;

    @NotBlank(message = "Status cannot be blank")
    private String status;

    @Positive(message = "Desired price must be a positive number")
    private double desiredPrice;

    @NotBlank(message = "Consignment type cannot be blank")
    private String consignmentType;

}
