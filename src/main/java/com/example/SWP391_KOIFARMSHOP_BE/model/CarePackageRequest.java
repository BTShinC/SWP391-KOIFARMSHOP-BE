package com.example.SWP391_KOIFARMSHOP_BE.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CarePackageRequest {
    @NotBlank(message = "Package name cannot be blank")
    @Size(max = 100, message = "Package name must be less than 100 characters")
    private String packageName;

    @Size(max = 500, message = "Description must be less than 500 characters")
    private String description;

    @NotNull(message = "Price cannot be null")
    @PositiveOrZero(message = "Price must be zero or positive")
    private double price;

    @NotNull(message = "Duration cannot be null")
    @PositiveOrZero(message = "Duration must be zero or positive")
    private int duration;

    @NotNull(message = "Food intake per day cannot be null")
    @PositiveOrZero(message = "Food intake per day must be zero or positive")
    private int foodIntakePerDay;
}