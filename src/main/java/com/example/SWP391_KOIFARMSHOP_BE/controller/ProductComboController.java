package com.example.SWP391_KOIFARMSHOP_BE.controller;

import com.example.SWP391_KOIFARMSHOP_BE.model.ProductComboRequest;
import com.example.SWP391_KOIFARMSHOP_BE.model.ProductComboResponse;
import com.example.SWP391_KOIFARMSHOP_BE.service.ProductComboService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/productcombo")
public class ProductComboController {
    @Autowired
    ProductComboService productComboService;


    // API để tạo sản phẩm mới
    @PostMapping
    public ResponseEntity<ProductComboResponse> createProductCombo(@Valid @RequestBody ProductComboRequest productComboRequest) {
        ProductComboResponse newProduct = productComboService.createProduct(productComboRequest);
        return ResponseEntity.ok(newProduct);
    }

    // API để lấy tất cả sản phẩm
    @GetMapping
    public ResponseEntity<List<ProductComboResponse>> getAllProductCombo() {
        List<ProductComboResponse> products = productComboService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    // API để lấy sản phẩm theo ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductComboResponse> getProductComboById(@PathVariable String id) {
        ProductComboResponse product = productComboService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    // API để lấy sản phẩm theo breed
    @GetMapping("breed/{breed}")
    public ResponseEntity<ProductComboResponse> getProductComboByBreed(@PathVariable String breed) {
        ProductComboResponse product = productComboService.getProductByBreed(breed);
        return ResponseEntity.ok(product);
    }

    // API để cập nhật sản phẩm
    @PutMapping("/{id}")
    public ResponseEntity<ProductComboResponse> updateProductCombo(@PathVariable String id, @Valid @RequestBody ProductComboRequest productComboRequest) {
        ProductComboResponse updatedProduct = productComboService.updateProduct(id, productComboRequest);
        return ResponseEntity.ok(updatedProduct);
    }

    // API để xóa sản phẩm
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProductCombo(@PathVariable String id) {
        productComboService.deleteProduct(id);
        return ResponseEntity.ok("Product deleted successfully");
    }
}