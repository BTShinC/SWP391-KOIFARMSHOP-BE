package com.example.SWP391_KOIFARMSHOP_BE.pojo;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Care_Package")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarePackage {

    @Id
    private String carePackageID;

    @NotBlank(message = "Package name cannot be blank")
    @Size(max = 100, message = "Package name must be less than 100 characters")
    private String packageName;

    @Size(max = 500, message = "Description must be less than 500 characters")
    private String description;

    @NotNull(message = "Price cannot be null")
    @PositiveOrZero(message = "Price must be zero or positive")
    private double price;

    @NotNull(message = "Duration cannot be null")
    @PositiveOrZero(message = "Duration must be zero or positive")
    private int duration;

    @NotNull(message = "Food Package cannot be null")
    private String foodPackage;

    @NotNull(message = "Tag cannot be null")
    private String tag;

    @NotNull(message = "Type cannot be null")
    private String type;

    // Lưu trữ JSON dưới dạng chuỗi TEXT trong MySQL
    @Lob
    @Column(columnDefinition = "TEXT")
    private String images; // Sẽ lưu chuỗi JSON của List<String>

    @Lob
    @Column(columnDefinition = "TEXT")
    private String services; // Sẽ lưu chuỗi JSON của List<String>

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "carepackage_id")
    private Set<Product> product;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "carepackage_id")
    private Set<ProductCombo> productCombos;

    // Phương thức để chuyển đổi danh sách thành JSON
    @Transient
    public List<String> getImagesAsList() {
        return JsonUtils.fromJson(images);
    }

    @Transient
    public void setImagesFromList(List<String> imagesList) {
        this.images = JsonUtils.toJson(imagesList);
    }

    @Transient
    public List<String> getServicesAsList() {
        return JsonUtils.fromJson(services);
    }

    @Transient
    public void setServicesFromList(List<String> servicesList) {
        this.services = JsonUtils.toJson(servicesList);
    }
}
