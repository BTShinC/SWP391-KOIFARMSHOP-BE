package com.example.SWP391_KOIFARMSHOP_BE.pojo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Product_Combo")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCombo {
    @Id
    private String productComboID;
    @NotBlank(message = "Name cannot be blank")
    private String comboName;
    @Positive(message = "Size must be a positive number")
    private float size;
    @NotBlank(message = "Breed cannot be blank")
    private String breed;
    @NotBlank(message = "Health status cannot be blank")
    private String healthStatus;
    @PositiveOrZero(message = "Quantity must be zero or a positive number")
    private int quantity;
    @NotBlank(message = "Description cannot be blank")
    private String description;
    @NotBlank(message = "Image URL cannot be blank")
    private String image;
    @Positive(message = "Price must be a positive number")
    private double price;
    @NotBlank(message = "Consignment type cannot be blank")
    private String consignmentType;
    @Positive(message = "Desired price must be a positive number")
    private double desiredPrice;
    @NotBlank(message = "Type cannot be blank")
    private String type;
    @Column(nullable = true)
    private String age;



    @NotBlank(message = "Type cannot be blank")
    private String status;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name ="ordersdetail_id")
    private OrdersDetail ordersdetail ;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name ="carepackage_id")
    private CarePackage carePackage;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "productCombo")
    private Consignment consignment;

}

