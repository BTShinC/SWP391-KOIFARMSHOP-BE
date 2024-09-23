package com.example.SWP391_KOIFARMSHOP_BE.pojo;

import jakarta.persistence.*;

import java.sql.Date;
@Entity
@Table(name = "Cosignment")
public class Consignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Consignment_ID")
    private long consignmentID;
    @Column(name = "Consignment_Date")
    private Date consignmentDate;
    @Column(name = "Status")
    private String status;
    @Column(name = "Product_ID")
    private long productID;
    @Column(name = "Product_Combo_ID")
    private long productComboID;

    public long getConsignmentID() {
        return consignmentID;
    }

    public void setConsignmentID(long consignmentID) {
        this.consignmentID = consignmentID;
    }

    public Date getConsignmentDate() {
        return consignmentDate;
    }

    public void setConsignmentDate(Date consignmentDate) {
        this.consignmentDate = consignmentDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getProductID() {
        return productID;
    }

    public void setProductID(long productID) {
        this.productID = productID;
    }

    public long getProductComboID() {
        return productComboID;
    }

    public void setProductComboID(long productComboID) {
        this.productComboID = productComboID;
    }

    public Consignment() {
    }

    public Consignment(long consignmentID, Date consignmentDate, String status, long productID, long productComboID) {
        this.consignmentID = consignmentID;
        this.consignmentDate = consignmentDate;
        this.status = status;
        this.productID = productID;
        this.productComboID = productComboID;
    }
}
