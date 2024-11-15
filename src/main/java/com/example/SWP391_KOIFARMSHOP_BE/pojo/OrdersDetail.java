package com.example.SWP391_KOIFARMSHOP_BE.pojo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "Orders_detail")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdersDetail {
    @Id
    private String ordersDetailID;
    @Column(name = "price")
    private double price;

    @Column(name = "discounted_price")
    private double discountedPrice;
    @Column(name = "type")
    private String type;
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_combo_id")
    private ProductCombo productCombo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orders_id")
    @JsonBackReference
    private Orders orders;
}

