package com.example.SWP391_KOIFARMSHOP_BE.model;

import lombok.Data;

@Data
public class AccountResponse {

    private double accountBalance;
    private String accountID;
    private  String fullName;
    private String address;
    private String email;
    private String phoneNumber;
    private String roleName;
    private String Token;


}
