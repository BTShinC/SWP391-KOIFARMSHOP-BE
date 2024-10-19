package com.example.SWP391_KOIFARMSHOP_BE.model;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.Date;

@Data
public class ConsignmentRequest {

    @NotNull(message = "Consignment date cannot be null")
    private Date consignmentDate;

    @NotNull(message = "Sale date cannot be null")
    private Date saleDate;

    @Positive(message = "Sale price must be a positive number")
    private double salePrice;

    @PastOrPresent(message = "Date received must be in the past or present")
    private Date dateReceived;

    @NotNull(message = "Date expiration cannot be null")
    @Future(message = "Date expiration must be in the future")
    private Date dateExpiration;

    @NotBlank(message = "Status cannot be blank")
    @Size(max = 50, message = "Status must be less than 50 characters")
    private String status;

    private String productID;
    private String productComboID;
}
