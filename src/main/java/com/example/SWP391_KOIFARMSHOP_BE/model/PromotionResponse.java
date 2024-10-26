package com.example.SWP391_KOIFARMSHOP_BE.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;
@Data
public class PromotionResponse {
    private String promotionID;
    private double discountValue;
    private String description;
    private Date startDate;
    private Date endDate;
}
