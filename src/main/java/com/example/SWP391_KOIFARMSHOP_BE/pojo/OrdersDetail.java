package com.example.SWP391_KOIFARMSHOP_BE.pojo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "Orders_detail")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdersDetail {
    @Id
    private String ordersDetailID;


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

