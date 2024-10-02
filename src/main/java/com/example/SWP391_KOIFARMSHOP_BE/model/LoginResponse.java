package com.example.SWP391_KOIFARMSHOP_BE.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {

    private String token; // JWT token
    private AccountResponse account; // Thông tin tài khoản

}
