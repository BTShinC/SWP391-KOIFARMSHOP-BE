package com.example.SWP391_KOIFARMSHOP_BE.model;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;
import java.util.List;

@Data
public class CareDetailRequest {

    @NotBlank(message = "Description cannot be blank")
    private String description;

    @NotNull(message = "Update date cannot be null")
    private Date updateDate;


    private String images;

    @NotBlank(message = "Consignment ID cannot be blank")
    private String consignmentID;
}
