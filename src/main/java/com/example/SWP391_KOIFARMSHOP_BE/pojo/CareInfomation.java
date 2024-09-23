package com.example.SWP391_KOIFARMSHOP_BE.pojo;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table (name = "Care_Infomation")
public class CareInfomation {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "Care_Infomation_ID")
    private long careInfomationID;
    @Column(name = "Date_Received")
    private Date dateReceived;
    @Column(name = "Date_Expiration")
    private Date dateExpiration;
    @Column(name = "Status")
    private String status;
    @Column(name = "Consignment_ID")
    private long consignmentID;

    public long getCareInfomationID() {
        return careInfomationID;
    }

    public void setCareInfomationID(long careInfomationID) {
        this.careInfomationID = careInfomationID;
    }

    public Date getDateReceived() {
        return dateReceived;
    }

    public void setDateReceived(Date dateReceived) {
        this.dateReceived = dateReceived;
    }

    public Date getDateExpiration() {
        return dateExpiration;
    }

    public void setDateExpiration(Date dateExpiration) {
        this.dateExpiration = dateExpiration;
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

    public CareInfomation() {
    }

    public CareInfomation(long careInfomationID, Date dateReceived, Date dateExpiration, String status, long consignmentID) {
        this.careInfomationID = careInfomationID;
        this.dateReceived = dateReceived;
        this.dateExpiration = dateExpiration;
        this.status = status;
        this.consignmentID = consignmentID;
    }
}
