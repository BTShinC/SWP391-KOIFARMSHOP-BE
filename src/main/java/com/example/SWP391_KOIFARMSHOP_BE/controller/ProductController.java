package com.example.SWP391_KOIFARMSHOP_BE.controller;


import com.example.SWP391_KOIFARMSHOP_BE.model.ProductRequest;
import com.example.SWP391_KOIFARMSHOP_BE.model.ProductResponse;
import com.example.SWP391_KOIFARMSHOP_BE.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@CrossOrigin
@RequestMapping("/api/products")

public class ProductController {

    @Autowired

    private ProductService productService;

    // API để tạo sản phẩm mới
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest productRequest) {
        ProductResponse newProduct = productService.createProduct(productRequest);
        return ResponseEntity.ok(newProduct);
    }

    // API để lấy tất cả sản phẩm
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        List<ProductResponse> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    // API để lấy sản phẩm theo ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable String id) {
        ProductResponse product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    // API để lấy sản phẩm theo breed
    @GetMapping("/breed/{breed}")
    public ResponseEntity<ProductResponse> getProductByBreed(@PathVariable String breed) {
        ProductResponse product = productService.getProductByBreed(breed);
        return ResponseEntity.ok(product);
    }

    // API để cập nhật sản phẩm
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable String id, @Valid @RequestBody ProductRequest productRequest) {
        ProductResponse updatedProduct = productService.updateProduct(id, productRequest);
        return ResponseEntity.ok(updatedProduct);
    }


    // API để xóa sản phẩm
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product deleted successfully");
    }
}