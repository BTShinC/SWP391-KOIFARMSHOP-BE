package com.example.SWP391_KOIFARMSHOP_BE.pojo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ShopCart")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopCart {

    @Id
    @Column(name = "shop_cart_id")
    private String shopCartID;

    @Column(name = "breed")
    private String breed;

    @Column(name = "price")
    private double price;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "type")  // "Product" hoặc "ProductCombo"
    private String type;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    // Liên kết với Product (nếu thêm sản phẩm)
    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "productID")
    private Product product;

    // Liên kết với ProductCombo (nếu thêm combo)
    @ManyToOne
    @JoinColumn(name = "product_combo_id", referencedColumnName = "productComboID")
    private ProductCombo productCombo;
}
