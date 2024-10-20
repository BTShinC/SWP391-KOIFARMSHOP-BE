package com.example.SWP391_KOIFARMSHOP_BE.model;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class AccountRequest {

    @NotBlank(message = "Full name cannot be blank")
    @Size(max = 100, message = "Full name cannot exceed 100 characters")
    private String fullName;

    @NotBlank(message = "Address cannot be blank")
    @Size(max = 255, message = "Address cannot exceed 255 characters")
    private String address;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Phone number cannot be blank")
    @Pattern(regexp = "^[0-9]+$", message = "Phone number should only contain numbers")
    @Size(min = 10, max = 15, message = "Phone number should be between 10 and 15 digits")
    private String phoneNumber;

    private double accountBalance;

    @Size(max = 255, message = "Image URL cannot exceed 255 characters")
    private String image;

    @NotBlank(message = "RoleID cannot be blank")
    private String roleID;
}
