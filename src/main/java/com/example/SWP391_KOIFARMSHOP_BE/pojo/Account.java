package com.example.SWP391_KOIFARMSHOP_BE.pojo;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "Account")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long accountID;
    @NotBlank(message = "Username cannot be blank")
    @Size(max = 50, message = "Username cannot exceed 50 characters")
    private String userName;
    @NotBlank(message = "Password cannot be blank")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;
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
    @Min(value = 0, message = "Account balance cannot be negative")
    private double accountBalance;
    @Size(max = 255, message = "Image URL cannot exceed 255 characters")
    private String image;


//  @ManyToOne(cascade = CascadeType.ALL)
//  @JoinColumn(name ="role_id",referencedColumnName = "RoleID")
//    private Role role ;
//
//  @OneToMany(cascade = CascadeType.ALL)
//    @JoinColumn(name = "account-id")
//    private Set<Orders> orders ;


//    @ManyToOne
//    @JoinColumn(name = "role_id", referencedColumnName = "roleID")
//    private Role role;

}

