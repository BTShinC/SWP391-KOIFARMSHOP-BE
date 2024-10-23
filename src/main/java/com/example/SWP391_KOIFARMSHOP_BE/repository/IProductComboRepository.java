package com.example.SWP391_KOIFARMSHOP_BE.repository;

import com.example.SWP391_KOIFARMSHOP_BE.pojo.Product;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.ProductCombo;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface IProductComboRepository extends JpaRepository<ProductCombo, String> {
    Optional<ProductCombo> findByComboName(String comboName);
    public ProductCombo findByBreed(String breed);
    ProductCombo findTopByOrderByProductComboIDDesc();
}
