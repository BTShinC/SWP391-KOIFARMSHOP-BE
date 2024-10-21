package com.example.SWP391_KOIFARMSHOP_BE.pojo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "AccountWithdrawal")
@Data
public class AccountWithdrawal {
    @Id
    private String accountWithdrawal;
    private Date date;
    private float pricesend;
}
