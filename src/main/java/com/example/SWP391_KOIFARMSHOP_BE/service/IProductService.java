package com.example.SWP391_KOIFARMSHOP_BE.service;

import com.example.SWP391_KOIFARMSHOP_BE.pojo.Product;

import java.util.List;
import java.util.Optional;

public interface IProductService {
    public List<Product> getAllProduct();
    public Product insertProduct(Product product);
    public Product updateProduct(long productID, Product product);
    public void deleteProduct (long productID);
    public Optional<Product> getProductByID(long productID);
}
