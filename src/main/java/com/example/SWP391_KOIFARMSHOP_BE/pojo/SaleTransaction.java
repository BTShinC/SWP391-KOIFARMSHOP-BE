package com.example.SWP391_KOIFARMSHOP_BE.pojo;

import jakarta.persistence.*;

import java.sql.Date;
@Entity
@Table(name = "Sale_Transaction")
public class SaleTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Sale_Transaction_ID")
    private long saleTransactionID;
    @Column(name = "Sale_Date")
    private Date saleDate;
    @Column(name = "Sale_Price")
    private double salePrice;
    @Column(name = "Status")
    private String status;
    @Column(name = "Consignment_ID")
    private long consignmentID;

    public long getSaleTransactionID() {
        return saleTransactionID;
    }

    public void setSaleTransactionID(long saleTransactionID) {
        this.saleTransactionID = saleTransactionID;
    }

    public Date getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getConsignmentID() {
        return consignmentID;
    }

    public void setConsignmentID(long consignmentID) {
        this.consignmentID = consignmentID;
    }

    public SaleTransaction() {
    }

    public SaleTransaction(long saleTransactionID, Date saleDate, double salePrice, String status, long consignmentID) {
        this.saleTransactionID = saleTransactionID;
        this.saleDate = saleDate;
        this.salePrice = salePrice;
        this.status = status;
        this.consignmentID = consignmentID;
    }
}
