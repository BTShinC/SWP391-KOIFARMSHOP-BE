package com.example.SWP391_KOIFARMSHOP_BE.pojo;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "Orders")
public class Orders {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "Orders_ID")
    private long ordersID;
    @Column(name = "Orders_Detail_ID")
    private long ordersDetailID;
    @Column(name = "Account_ID")
    private long accountID;
    @Column(name = "Status")
    private String status;
    @Column(name = "Total")
    private double total;
    @Column(name = "Date")
    private Date date;
    @Column(name = "Desctription")
    private String description;

    public long getOrdersID() {
        return ordersID;
    }

    public void setOrdersID(long ordersID) {
        this.ordersID = ordersID;
    }

    public long getOrdersDetailID() {
        return ordersDetailID;
    }

    public void setOrdersDetailID(long ordersDetailID) {
        this.ordersDetailID = ordersDetailID;
    }

    public long getAccountID() {
        return accountID;
    }

    public void setAccountID(long accountID) {
        this.accountID = accountID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Orders() {
    }

    public Orders(long ordersID, long ordersDetailID, long accountID, String status, double total, Date date, String description) {
        this.ordersID = ordersID;
        this.ordersDetailID = ordersDetailID;
        this.accountID = accountID;
        this.status = status;
        this.total = total;
        this.date = date;
        this.description = description;
    }
}
