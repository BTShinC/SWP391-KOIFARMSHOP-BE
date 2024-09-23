package com.example.SWP391_KOIFARMSHOP_BE.pojo;

import jakarta.persistence.*;

@Entity
@Table(name = "Feedback")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Feedback_ID")
    private long feedBackID;
    @Column(name = "Desciption")
    private String desciption;
    @Column(name = "Orders_ID")
    private String ordersID;

    public long getFeedBackID() {
        return feedBackID;
    }

    public void setFeedBackID(long feedBackID) {
        this.feedBackID = feedBackID;
    }

    public String getDesciption() {
        return desciption;
    }

    public void setDesciption(String desciption) {
        this.desciption = desciption;
    }

    public String getOrdersID() {
        return ordersID;
    }

    public void setOrdersID(String ordersID) {
        this.ordersID = ordersID;
    }

    public Feedback() {
    }

    public Feedback(long feedBackID, String desciption, String ordersID) {
        this.feedBackID = feedBackID;
        this.desciption = desciption;
        this.ordersID = ordersID;
    }
}
