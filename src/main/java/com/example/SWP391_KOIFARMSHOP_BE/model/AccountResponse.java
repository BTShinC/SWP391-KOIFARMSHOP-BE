package com.example.SWP391_KOIFARMSHOP_BE.model;

import lombok.Data;

@Data
public class AccountResponse {
    private long accountID;
    private  String fullName;
    private String address;
    private String email;
    private String phoneNumber;
    private String Role;

}
