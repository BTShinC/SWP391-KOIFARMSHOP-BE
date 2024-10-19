package com.example.SWP391_KOIFARMSHOP_BE.pojo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@Table(name = "Transaction")
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @Id
    private  String transactionID;

    private String accountID;
    private double price;
    private Date date ;
    private String status;
    private String image;

}
