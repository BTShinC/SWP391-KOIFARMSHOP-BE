package com.example.SWP391_KOIFARMSHOP_BE.pojo;

import jakarta.persistence.*;

@Entity
@Table(name = "Orders_detail")

public class OrdersDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Orders_Detail_ID")
    private long ordersDetailID;
    @Column(name = "Product_ID")
    private long productID;
    @Column(name = "Orders_ID")
    private long ordersID;
    @Column(name = "Product_Combo_ID")
    private long productComboID;

    public long getOrdersDetailID() {
        return ordersDetailID;
    }

    public void setOrdersDetailID(long ordersDetailID) {
        this.ordersDetailID = ordersDetailID;
    }

    public long getProductID() {
        return productID;
    }

    public void setProductID(long productID) {
        this.productID = productID;
    }

    public long getOrdersID() {
        return ordersID;
    }

    public void setOrdersID(long ordersID) {
        this.ordersID = ordersID;
    }

    public long getProductComboID() {
        return productComboID;
    }

    public void setProductComboID(long productComboID) {
        this.productComboID = productComboID;
    }

    public OrdersDetail() {
    }

    public OrdersDetail(long ordersDetailID, long productID, long ordersID, long productComboID) {
        this.ordersDetailID = ordersDetailID;
        this.productID = productID;
        this.ordersID = ordersID;
        this.productComboID = productComboID;
    }
}
