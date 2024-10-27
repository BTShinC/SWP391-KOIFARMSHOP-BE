package com.example.SWP391_KOIFARMSHOP_BE.model;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class QuestionRequest {

    @NotBlank(message = "Username cannot be blank")
    @Size(max = 50, message = "Username cannot exceed 50 characters")
    private String userName;

    @Column(name = "contact")
    @NotBlank(message = "Phone number cannot be blank")
    @Pattern(regexp = "^[0-9]+$", message = "Phone number should only contain numbers")
    @Size(min = 10, max = 15, message = "Phone number should be between 10 and 15 digits")
    private String contact;


    @NotBlank(message = "Topic cannot be blank")
    private String topic;

    @NotBlank(message = " Question cannot be blank")
    private String question;

    @NotBlank(message = "Status cannot be blank")
    private String status;
}
