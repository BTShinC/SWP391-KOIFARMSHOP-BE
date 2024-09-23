package com.example.SWP391_KOIFARMSHOP_BE.pojo;

import jakarta.persistence.*;

@Entity
@Table(name = "Product")
public class Product {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "Product_ID")
    private long productID;
    @Column(name = "Breed")
    private String breed;
    @Column(name = "Size")
    private float size;
    @Column(name = "Sex")
    private String sex;
    @Column(name = "Health_Status")
    private String healthStatus;
    @Column(name = "Personality_Trait")
    private String personalityTrait;
    @Column(name = "Origin")
    private String origin;
    @Column(name = "Description")
    private String desciption;
    @Column(name = "Image")
    private String image;
    @Column(name = "Price")
    private double price;
    @Column(name = "Certificate")
    private String certificate;
    @Column(name = "Type")
    private String type;
    @Column(name = "Quantity")
    private int quantity;
    @Column(name = "Status")
    private String status;
    @Column(name = "Desired_Price")
    private double desiredPrice;
    @Column(name = "Consignment_Type")
    private String consignmentType;
    @Column(name = "Care_Package_ID")
    private long carePackageID;

    public long getProductID() {
        return productID;
    }

    public void setProductID(long productID) {
        this.productID = productID;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getHealthStatus() {
        return healthStatus;
    }

    public void setHealthStatus(String healthStatus) {
        this.healthStatus = healthStatus;
    }

    public String getPersonalityTrait() {
        return personalityTrait;
    }

    public void setPersonalityTrait(String personalityTrait) {
        this.personalityTrait = personalityTrait;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
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

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getDesiredPrice() {
        return desiredPrice;
    }

    public void setDesiredPrice(double desiredPrice) {
        this.desiredPrice = desiredPrice;
    }

    public String getConsignmentType() {
        return consignmentType;
    }

    public void setConsignmentType(String consignmentType) {
        this.consignmentType = consignmentType;
    }

    public long getCarePackageID() {
        return carePackageID;
    }

    public void setCarePackageID(long carePackageID) {
        this.carePackageID = carePackageID;
    }

    public Product() {
    }

    public Product(long productID, String breed, String sex, float size, String healthStatus, String personalityTrait, String origin, String desciption, String image, double price, String certificate, String type, int quantity, String status, double desiredPrice, String consignmentType, long carePackageID) {
        this.productID = productID;
        this.breed = breed;
        this.sex = sex;
        this.size = size;
        this.healthStatus = healthStatus;
        this.personalityTrait = personalityTrait;
        this.origin = origin;
        this.desciption = desciption;
        this.image = image;
        this.price = price;
        this.certificate = certificate;
        this.type = type;
        this.quantity = quantity;
        this.status = status;
        this.desiredPrice = desiredPrice;
        this.consignmentType = consignmentType;
        this.carePackageID = carePackageID;
    }
}

