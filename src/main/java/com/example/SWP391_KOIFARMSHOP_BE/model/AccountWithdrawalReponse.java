package com.example.SWP391_KOIFARMSHOP_BE.model;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
public class AccountWithdrawalReponse {

    private String accountWithdrawalId;
    private Date date;
    private float pricesend;
    private String status;
    private String accountID;
    private String account_number;
    private String account_holder_name;
    private String bank_branch;
    private String bank_name;
    private String description;
}
