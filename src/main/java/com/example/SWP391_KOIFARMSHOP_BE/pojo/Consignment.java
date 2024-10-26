package com.example.SWP391_KOIFARMSHOP_BE.pojo;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "Consignment")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Consignment {

    @Id
    @Column(name = "consignment_id")
    private String consignmentID;

    private Date consignmentDate;

    private Date saleDate;

    private double salePrice;

    @PastOrPresent(message = "Date received must be in the past or present")
    private Date dateReceived;

    @Future(message = "Date expiration must be in the future")
    private Date dateExpiration;

    @NotBlank(message = "Status cannot be blank")
    @Size(max = 50, message = "Status must be less than 50 characters")
    private String status;
    private double total;
    private String farmName;
    private String reason;
    private String consignmentType;
    private int duration;
    private String accountID;
    // Mối quan hệ với ProductCombo
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_combo_id")
    private ProductCombo productCombo;

    // Mối quan hệ với Product
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private Product product;
}
