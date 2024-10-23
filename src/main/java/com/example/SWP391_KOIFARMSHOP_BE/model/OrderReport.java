package com.example.SWP391_KOIFARMSHOP_BE.model;

import lombok.Data;

import java.util.Date;

@Data
public class OrderReport {
    private String orderID;
    private String status;
    private double total;
    private Date date;
}
