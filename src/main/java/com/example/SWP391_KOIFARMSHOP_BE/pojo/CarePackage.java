package com.example.SWP391_KOIFARMSHOP_BE.pojo;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


@Entity
@Table (name = "Care_Package")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarePackage {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)

    private long carePackageID;
    @NotBlank(message = "Package name cannot be blank")
    @Size(max = 100, message = "Package name must be less than 100 characters")
    private String packageName;
    @Size(max = 500, message = "Description must be less than 500 characters")
    private String description;
    @NotNull(message = "Price cannot be null")
    @PositiveOrZero(message = "Price must be zero or positive")
    private double price;
    @NotNull(message = "Duration cannot be null")
    @Min(value = 1, message = "Duration must be at least 1 day")
    private int duration;
    @NotNull(message = "Food intake per day cannot be null")
    @Min(value = 1, message = "Food intake per day must be at least 1")
    private int foodIntakePerDay;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name ="carepackage_id")
    private Set<Product> product;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name ="carepackage_id")
    private Set<ProductCombo> productCombos;

}
