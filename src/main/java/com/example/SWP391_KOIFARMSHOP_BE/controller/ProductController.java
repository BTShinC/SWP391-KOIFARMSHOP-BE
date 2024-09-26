package com.example.SWP391_KOIFARMSHOP_BE.controller;

import com.example.SWP391_KOIFARMSHOP_BE.pojo.Product;
import com.example.SWP391_KOIFARMSHOP_BE.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private IProductService iProductService;
    @GetMapping("/")
    public ResponseEntity<List<Product>> fetchALlProduct(){
        return ResponseEntity.ok(iProductService.getAllProduct());
    }
    @PostMapping("/")
    @ResponseStatus (HttpStatus.CREATED)
    public Product saveProduct(@RequestBody Product product){
        return iProductService.insertProduct(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable long id, @RequestBody Product product){
        Product updateProduct = iProductService.updateProduct(id, product);
        return ResponseEntity.ok(updateProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable long id){
        iProductService.deleteProduct(id);
        return ResponseEntity.ok("Delete product success!");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Product>> getProductByID(@PathVariable long id){
        Optional<Product> product = iProductService.getProductByID(id);
        return  ResponseEntity.ok(product);
    }
}

