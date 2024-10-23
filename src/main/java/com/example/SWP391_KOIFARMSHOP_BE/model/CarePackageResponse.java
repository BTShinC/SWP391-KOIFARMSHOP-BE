package com.example.SWP391_KOIFARMSHOP_BE.model;

import lombok.Data;

import java.util.List;

@Data
public class CarePackageResponse {

    private String carePackageID;
    private String packageName;
    private String description;
    private double price;
    private int duration;
    private String foodPackage;
    private String tag;
    private String type;

    private List<String> images;

    private List<String> services;
}
