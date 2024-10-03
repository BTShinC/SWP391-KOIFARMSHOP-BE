package com.example.SWP391_KOIFARMSHOP_BE.pojo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.Set;

@Entity
@Table(name = "Cosignment")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Consignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long consignmentID;
    @NotNull(message = "Consignment date cannot be null")
    private Date consignmentDate;
    @NotBlank(message = "Status cannot be blank")
    @Size(max = 50, message = "Status must be less than 50 characters")
    private String status;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_combo_id")
    private ProductCombo productCombo;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private Product product;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "consignment")
    private SaleTransaction saleTransaction;


    @OneToOne(cascade = CascadeType.ALL, mappedBy = "consignment")
    private CareInfomation careInfomation;
}
