package com.example.SWP391_KOIFARMSHOP_BE.pojo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
@Entity
@Table(name = "Sale_Transaction")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long saleTransactionID;
    @NotNull(message = "Sale date cannot be null")
    private Date saleDate;
    @Positive(message = "Sale price must be a positive number")
    private double salePrice;
    @NotBlank(message = "Status cannot be blank")
    private String status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "consigment_id")
    private Consignment consignment;


}
