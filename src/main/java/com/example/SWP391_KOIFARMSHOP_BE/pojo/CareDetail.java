package com.example.SWP391_KOIFARMSHOP_BE.pojo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Care_Detail")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CareDetail {

    @Id
    @Column(name = "care_detail_id")
    private String careDetailID;

    @Column(columnDefinition = "TEXT")
    private String description;

    @NotNull(message = "Update date cannot be null")
    private Date updateDate;


    private String images;


    // Relationship with Consignment entity
    @ManyToOne
    @JoinColumn(name = "consignment_id", referencedColumnName = "consignment_id")
    private Consignment consignment;
}
