package com.example.SWP391_KOIFARMSHOP_BE.pojo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "Role")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    @Id
    private String roleID;
    @NotBlank(message = "Username cannot be blank")
    @Size(max = 50, message = "Username cannot exceed 50 characters")
    private String roleName;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name ="role_id")
    private Set<Account> account;


    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL) // Chỉ định mappedBy để tránh vấn đề vòng lặp
    private Set<Account> accounts;


}
