package com.example.SWP391_KOIFARMSHOP_BE.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;
@Data
public class PromotionRequest {
    private double discountValue;
    private String description;
    @NotNull(message = "Start date cannot be null")
    private Date startDate;
    @NotNull(message = "End date cannot be null")
    private Date endDate;
}
