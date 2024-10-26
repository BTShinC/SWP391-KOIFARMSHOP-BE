package com.example.SWP391_KOIFARMSHOP_BE.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
public class AccountWithdrawalRequest {
    @NotNull(message = "Date cannot be null")
    private Date date;

    @NotNull(message = "Price cannot be null")
    private float pricesend;

    @NotBlank(message = "Status cannot be blank")
    private String status;

    @NotBlank(message = "Account ID cannot be blank")
    private String accountId;
}
