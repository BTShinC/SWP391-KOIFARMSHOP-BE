package com.example.SWP391_KOIFARMSHOP_BE.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.sql.Date;

@Data
public class OrdersRequest {
    @NotBlank(message = "Status cannot be blank")
    @Size(max = 50, message = "Status must be less than 50 characters")
    private String status;

    @PositiveOrZero(message = "Total must be zero or a positive number")
    private double total;

    @NotNull(message = "Date cannot be null")
    private Date date;

    @Size(max = 255, message = "Description must be less than 255 characters")
    private String description;

    @NotNull(message = "Account ID cannot be null")
    private long accountId;

    private long feedbackId;
}
