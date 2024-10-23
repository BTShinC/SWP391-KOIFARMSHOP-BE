package com.example.SWP391_KOIFARMSHOP_BE.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Date;

@Data
public class AccountWithdrawalRequest {
    @NotBlank(message = "Full name cannot be blank")
    private Date date;
    @NotBlank(message = "Full name cannot be blank")
    private float pricesend;
    @NotBlank(message = "Full name cannot be blank")
    private String accountId;
}
