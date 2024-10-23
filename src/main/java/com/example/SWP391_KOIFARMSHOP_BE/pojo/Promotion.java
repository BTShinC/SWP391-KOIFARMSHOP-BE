package com.example.SWP391_KOIFARMSHOP_BE.pojo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "promotion")
@Data
public class Promotion {
    @Id
    @Column (name = "promotionID")
    private String promotionID;
    private double discountValue;
    private String description;
    @NotNull(message = "Start date cannot be null")
    private Date startDate;
    @NotNull(message = "End date cannot be null")
    private Date endDate;

}
