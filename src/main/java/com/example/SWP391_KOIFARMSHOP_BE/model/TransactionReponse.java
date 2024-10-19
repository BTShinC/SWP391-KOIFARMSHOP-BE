package com.example.SWP391_KOIFARMSHOP_BE.model;

import lombok.Data;

import java.util.Date;

@Data
public class TransactionReponse {

    private  String TransactionID;

    private String accountID;
    private double price;
    private Date date ;
    private String Status;
    private String image;
}
