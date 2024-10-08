package com.example.SWP391_KOIFARMSHOP_BE.pojo;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Feedback")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Feedback {
    @Id
    private String feedbackID;
    @Size(max = 500, message = "Description must be less than 500 characters")
    private String desciption;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "feedback")
    private Orders orders;



}
