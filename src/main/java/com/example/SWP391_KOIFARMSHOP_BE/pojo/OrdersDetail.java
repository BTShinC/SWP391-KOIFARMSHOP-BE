package com.example.SWP391_KOIFARMSHOP_BE.pojo;

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


    @ManyToOne
    @JoinColumn(name = "order_id")
    private Orders orders;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "productID")
    private Product product; // Lưu productID

    @ManyToOne
    @JoinColumn(name = "product_combo_id", referencedColumnName = "productComboID")
    private ProductCombo productCombo; // Lưu productComboID
}

