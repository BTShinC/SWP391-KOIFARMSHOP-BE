package com.example.SWP391_KOIFARMSHOP_BE.model;

import lombok.Data;

@Data
public class CarePackageResponse {
    private String carePackageID;
    private String packageName;
    private String description;
    private double price;
    private int duration;
    private int foodIntakePerDay;
}
