package com.example.SWP391_KOIFARMSHOP_BE.pojo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "Total_Revenue")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TotalRevenue {

    @Id
    @Column(name = "Total_Revenue_ID")
    private String totalRevenueID;

    @Temporal(TemporalType.DATE)
    @Column(name = "start_date")
    private Date startDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "total_revenue")
    private double totalRevenue;


}
