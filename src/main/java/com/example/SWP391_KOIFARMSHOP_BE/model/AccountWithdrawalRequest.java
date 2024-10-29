package com.example.SWP391_KOIFARMSHOP_BE.model;

import jakarta.persistence.Column;
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

    @NotBlank(message = "Account ID cannot be blank")
    @Column(name = "account_id")
    private String accountID;
    @NotBlank(message = "account_number cannot be blank")
    private String account_number;
    @NotBlank(message = "account_holder_name cannot be blank")
    private String account_holder_name;
    @NotBlank(message = "bank_branch cannot be blank")
    private String bank_branch;
    @NotBlank(message = "bank_name cannot be blank")
    private String bank_name;
    @NotBlank(message = "description cannot be blank")
    private String description;
}
