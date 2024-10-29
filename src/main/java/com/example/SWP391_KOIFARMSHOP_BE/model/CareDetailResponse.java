package com.example.SWP391_KOIFARMSHOP_BE.model;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CareDetailResponse {

    private String careDetailID;
    private String description;
    private Date updateDate;
    private String images;
    private String consignmentID;
}
