package com.example.SWP391_KOIFARMSHOP_BE.pojo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "AccountWithdrawal")
@Data
public class AccountWithdrawal {
    @Id
    private String accountWithdrawalId;
    @NotBlank(message = "Full name cannot be blank")
    private Date date;
    @NotBlank(message = "Full name cannot be blank")
    private float pricesend;
    @NotBlank(message = "Full name cannot be blank")
    private String status;
    @NotBlank(message = "Full name cannot be blank")
    private String accountId;
}
