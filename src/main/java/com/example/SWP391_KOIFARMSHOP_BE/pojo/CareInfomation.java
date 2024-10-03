package com.example.SWP391_KOIFARMSHOP_BE.pojo;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Table (name = "Care_Infomation")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CareInfomation {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)

    private long careInfomationID;
    @NotNull(message = "Date received cannot be null")
    @PastOrPresent(message = "Date received must be in the past or present")
    private Date dateReceived;
    @NotNull(message = "Date expiration cannot be null")
    @Future(message = "Date expiration must be in the future")
    private Date dateExpiration;
    @NotBlank(message = "Status cannot be blank")
    @Size(max = 255, message = "Status must be less than 255 characters")
    private String status;



    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "consigment_id")
    private Consignment consignment;

}
