package com.example.SWP391_KOIFARMSHOP_BE.pojo;

import jakarta.persistence.*;

@Entity
@Table(name = "Product_Combo")
public class ProductCombo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Product_Combo_ID")
    private long productComboID;
    @Column(name = "Size")
    private float size;
    @Column(name = "Breed")
    private String breed;
    @Column(name = "Health_Status")
    private String healthStatus;
    @Column(name = "Quantity")
    private int quantity;
    @Column(name = "Description")
    private String desciption;
    @Column(name = "image")
    private String image;
    @Column(name = "Price")
    private double price;
    @Column(name = "Consignment_Type")
    private String consignmentType;
    @Column(name = "Desired_Price")
    private double desiredPrice;
    @Column(name = "Type")
    private String type;
    @Column(name = "Care_Package_ID")
    private long carePackageID;

    public long getProductComboID() {
        return productComboID;
    }

    public void setProductComboID(long productComboID) {
        this.productComboID = productComboID;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getHealthStatus() {
        return healthStatus;
    }

    public void setHealthStatus(String healthStatus) {
        this.healthStatus = healthStatus;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDesciption() {
        return desciption;
    }

    public void setDesciption(String desciption) {
        this.desciption = desciption;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getConsignmentType() {
        return consignmentType;
    }

    public void setConsignmentType(String consignmentType) {
        this.consignmentType = consignmentType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getDesiredPrice() {
        return desiredPrice;
    }

    public void setDesiredPrice(double desiredPrice) {
        this.desiredPrice = desiredPrice;
    }

    public long getCarePackageID() {
        return carePackageID;
    }

    public void setCarePackageID(long carePackageID) {
        this.carePackageID = carePackageID;
    }

    public ProductCombo() {
    }

    public ProductCombo(long productComboID, float size, String breed, int quantity, String healthStatus, String desciption, String image, double price, String consignmentType, double desiredPrice, String type, long carePackageID) {
        this.productComboID = productComboID;
        this.size = size;
        this.breed = breed;
        this.quantity = quantity;
        this.healthStatus = healthStatus;
        this.desciption = desciption;
        this.image = image;
        this.price = price;
        this.consignmentType = consignmentType;
        this.desiredPrice = desiredPrice;
        this.type = type;
        this.carePackageID = carePackageID;
    }
}

