package com.example.SWP391_KOIFARMSHOP_BE.pojo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Product")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    private String productID;
    @NotBlank(message = "Product name cannot be blank")
    private String productName;
    @NotBlank(message = "Breed cannot be blank")
    private String breed;

    @Positive(message = "Size must be a positive number")
    private float size;

    @NotBlank(message = "Sex cannot be blank")
    private String sex;

    @NotBlank(message = "Health status cannot be blank")
    private String healthStatus;

    @NotBlank(message = "Personality trait cannot be blank")
    private String personalityTrait;

    @NotBlank(message = "Origin cannot be blank")
    private String origin;

    @NotBlank(message = "Description cannot be blank")

    private String description;
    @NotBlank(message="Image Url canot br blank")

    private String image;

    @Positive(message = "Price must be a positive number")
    private double price;

    @NotBlank(message = "Certificate cannot be blank")
    private String certificate;

    @NotBlank(message = "Type cannot be blank")
    private String type;

    @PositiveOrZero(message = "Quantity must be zero or a positive number")
    private int quantity;

    @NotBlank(message = "Status cannot be blank")
    private String status;

    @Positive(message = "Desired price must be a positive number")
    private double desiredPrice;

    @NotBlank(message = "Consignment type cannot be blank")
    private String consignmentType;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name ="ordersdetail_id")
    private OrdersDetail ordersdetail ;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name ="carepackage_id")
    private CarePackage carePackage  ;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "product")
    private Consignment consignment;

}

