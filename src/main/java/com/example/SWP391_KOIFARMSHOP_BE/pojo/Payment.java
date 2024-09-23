package com.example.SWP391_KOIFARMSHOP_BE.pojo;

import jakarta.persistence.*;

@Entity
@Table(name = "Payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Payment_ID")
    private long paymentID;
    @Column(name = "Orders_ID")
    private long ordersID;
    @Column(name = "Sale_Price")
    private double salePrice;
    @Column(name = "Ship_Price")
    private double shipPrice;
    @Column(name = "Sub_Total")
    private double subTotal;

    public long getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(long paymentID) {
        this.paymentID = paymentID;
    }

    public long getOrdersID() {
        return ordersID;
    }

    public void setOrdersID(long ordersID) {
        this.ordersID = ordersID;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    public double getShipPrice() {
        return shipPrice;
    }

    public void setShipPrice(double shipPrice) {
        this.shipPrice = shipPrice;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public Payment() {
    }

    public Payment(long paymentID, long ordersID, double salePrice, double shipPrice, double subTotal) {
        this.paymentID = paymentID;
        this.ordersID = ordersID;
        this.salePrice = salePrice;
        this.shipPrice = shipPrice;
        this.subTotal = subTotal;
    }
}
