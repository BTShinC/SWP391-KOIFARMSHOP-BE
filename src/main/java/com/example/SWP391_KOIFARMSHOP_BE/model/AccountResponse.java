package com.example.SWP391_KOIFARMSHOP_BE.model;

import lombok.Data;

@Data
public class AccountResponse {
    long accountID;
    String fullName;
    String address;
    String email;
    String phoneNumber;
}
