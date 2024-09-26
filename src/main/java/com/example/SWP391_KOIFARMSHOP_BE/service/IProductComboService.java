package com.example.SWP391_KOIFARMSHOP_BE.service;

import com.example.SWP391_KOIFARMSHOP_BE.pojo.ProductCombo;

import java.util.List;
import java.util.Optional;

public interface IProductComboService {
    public List<ProductCombo> getAllProductCombo();
    public ProductCombo insertProductCombo(ProductCombo productCombo);
    public ProductCombo updateProductCombo(long productComboID, ProductCombo productCombo);
    public void deleteProductCombo (long productComboID);
    public Optional<ProductCombo> getProductComboByID(long productComboID);
}
