package com.example.SWP391_KOIFARMSHOP_BE.pojo;

import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Payment")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    @Id
    private String paymentID;
    @PositiveOrZero(message = "Sale price must be zero or a positive number")
    private double salePrice;
    @PositiveOrZero(message = "Ship price must be zero or a positive number")
    private double shipPrice;
    @PositiveOrZero(message = "Subtotal must be zero or a positive number")
    private double subTotal;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private Orders orders;



}
