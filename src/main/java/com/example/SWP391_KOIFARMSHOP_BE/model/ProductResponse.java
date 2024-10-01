package com.example.SWP391_KOIFARMSHOP_BE.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ProductResponse {

     private long productID;
     private   String breed;
     private float size;
     private   String sex;
     private  String healthStatus;
     private  String personalityTrait;
     private String origin;
     private String description;
     private String image;
     private double price;
     private String certificate;
     private  String type;
     private  int quantity;
     private  String status;
     private  double desiredPrice;
     private  String consignmentType;
}
