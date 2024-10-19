package com.example.SWP391_KOIFARMSHOP_BE.service;

import com.example.SWP391_KOIFARMSHOP_BE.exception.EntityNotFoundException;
import com.example.SWP391_KOIFARMSHOP_BE.model.*;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.Product;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.ProductCombo;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.Role;
import com.example.SWP391_KOIFARMSHOP_BE.repository.IProductComboRepository;
import com.example.SWP391_KOIFARMSHOP_BE.repository.IProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class ProductComboService {
    @Autowired
    IProductComboRepository iProductComboRepository;


    @Autowired
    private ModelMapper modelMapper;

    // Tạo sản phẩm mới
    public ProductComboResponse createProduct(ProductComboRequest productComboRequest) {
        Optional<ProductCombo> existingProduct = iProductComboRepository.findByComboName(productComboRequest.getComboName());

        if (existingProduct.isPresent()) {
            throw new IllegalArgumentException("Product with name '" + productComboRequest.getComboName() + "' already exists.");
        }
        String nextId = generateNextProductComboId();
        ProductCombo product = modelMapper.map(productComboRequest, ProductCombo.class);
        product.setProductComboID(nextId);
        product.setConsignment(null);
        product.setCarePackage(null);
        product.setOrdersdetail(null);
        ProductCombo savedProduct = iProductComboRepository.save(product);
        return modelMapper.map(savedProduct, ProductComboResponse.class);
    }
    private String generateNextProductComboId() {
        ProductCombo lastProduct = iProductComboRepository.findTopByOrderByProductComboIDDesc();
        if (lastProduct != null) {
            String lastId = lastProduct.getProductComboID();
            int idNumber = Integer.parseInt(lastId.substring(2));
            String nextId = String.format("PC%03d", idNumber + 1);
            return nextId;
        } else {
            return "PC001";
        }
    }

    // Lấy tất cả sản phẩm
    public List<ProductComboResponse> getAllProducts() {
        List<ProductCombo> products = iProductComboRepository.findAll();
        return products.stream()
                .map(productCombo -> modelMapper.map(productCombo, ProductComboResponse.class))
                .collect(Collectors.toList());
    }

    // Lấy sản phẩm theo ID
    public ProductComboResponse getProductById(String productId) {
        ProductCombo product = iProductComboRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product combo with ID " + productId + " not found"));
        return modelMapper.map(product, ProductComboResponse.class);
    }
    // Lấy sản phẩm theo breed
    public ProductComboResponse getProductByBreed(String breed){
        ProductCombo product = iProductComboRepository.findByBreed(breed);
        if (product == null) {
            throw new IllegalArgumentException("Product with name '" + breed + "' already exists.");
        }
        return modelMapper.map(product, ProductComboResponse.class);
    }

    // Cập nhật sản phẩm
    public ProductComboResponse updateProduct(String productId, ProductComboRequest productComboRequest) {
        ProductCombo product = iProductComboRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product combo with ID " + productId + " not found"));

        // Cập nhật thông tin sản phẩm
        modelMapper.map(productComboRequest, product);
        product.setConsignment(null);
        product.setCarePackage(null);
        product.setOrdersdetail(null);
        ProductCombo updatedProduct = iProductComboRepository.save(product);
        return modelMapper.map(updatedProduct, ProductComboResponse.class);
    }

    // Xóa sản phẩm
    public void deleteProduct(String productId) {
        ProductCombo product = iProductComboRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product combo with ID " + productId + " not found"));
        iProductComboRepository.delete(product);

    }
    }