package com.example.SWP391_KOIFARMSHOP_BE.model;

import lombok.Data;

import java.util.Date;

@Data
public class ConsignmentResponse {

    private String consignmentID;
    private Date consignmentDate;
    private Date saleDate;
    private double salePrice;
    private Date dateReceived;
    private Date dateExpiration;
    private String status;
    private double toltal;
    private String accountID;
    private String productID;
    private String productComboID;
}
