package com.example.SWP391_KOIFARMSHOP_BE.model;

import lombok.Data;

@Data
public class FeedbackResponse {
    private String feedbackID;
    private String description;
    private String image;
    private String orderID;
    private String accountID;
}
