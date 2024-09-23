package com.example.SWP391_KOIFARMSHOP_BE.pojo;

import jakarta.persistence.*;



@Entity
@Table (name = "Care_Package")
public class CarePackage {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "Care_Package_ID")
    private long carePackageID;
    @Column(name = "Package_Name")
    private String packageName;
    @Column(name = "Description")
    private String description;
    @Column(name = "Price")
    private double price;
    @Column(name = "Duration")
    private int duration;
    @Column(name = "Food_Intake_Per_Day")
    private int foodIntakePerDay;
    @Column(name = "Product_ID")
    private long productID;
    @Column(name = "Product_Combo_ID")
    private long productComboID;

    public long getCarePackageID() {
        return carePackageID;
    }

    public void setCarePackageID(long carePackageID) {
        this.carePackageID = carePackageID;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getFoodIntakePerDay() {
        return foodIntakePerDay;
    }

    public void setFoodIntakePerDay(int foodIntakePerDay) {
        this.foodIntakePerDay = foodIntakePerDay;
    }

    public long getProductID() {
        return productID;
    }

    public void setProductID(long productID) {
        this.productID = productID;
    }

    public long getProductComboID() {
        return productComboID;
    }

    public void setProductComboID(long productComboID) {
        this.productComboID = productComboID;
    }

    public CarePackage() {
    }

    public CarePackage(long carePackageID, String packageName, String description, double price, long productID, int foodIntakePerDay, int duration, long productComboID) {
        this.carePackageID = carePackageID;
        this.packageName = packageName;
        this.description = description;
        this.price = price;
        this.productID = productID;
        this.foodIntakePerDay = foodIntakePerDay;
        this.duration = duration;
        this.productComboID = productComboID;
    }
}
