package com.example.SWP391_KOIFARMSHOP_BE.model;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class FeedbackRequest {
    @Size(max = 500, message = "Description must be less than 500 characters")
    private String desciption;

    private String orderID;
}
