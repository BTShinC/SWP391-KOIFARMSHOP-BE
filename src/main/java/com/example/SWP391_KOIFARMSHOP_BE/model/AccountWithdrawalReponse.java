package com.example.SWP391_KOIFARMSHOP_BE.model;

import lombok.Data;

import java.util.Date;

@Data
public class AccountWithdrawalReponse {
    private String accountWithdrawalId;
    private Date date;
    private float pricesend;
    private String status;
    private String accountId;
}
