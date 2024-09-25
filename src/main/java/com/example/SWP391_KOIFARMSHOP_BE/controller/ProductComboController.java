package com.example.SWP391_KOIFARMSHOP_BE.controller;

import com.example.SWP391_KOIFARMSHOP_BE.pojo.ProductCombo;
import com.example.SWP391_KOIFARMSHOP_BE.service.IProductComboService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/productCombo")
public class ProductComboController {
    @Autowired
    private IProductComboService iProductComboService;
    @GetMapping("/")
    public ResponseEntity<List<ProductCombo>> fetchALlProductCombo(){
        return ResponseEntity.ok(iProductComboService.getAllProductCombo());
    }
    @PostMapping("/")
    @ResponseStatus (HttpStatus.CREATED)
    public ProductCombo saveProductCombo(@Valid @RequestBody ProductCombo productCombo){
        return iProductComboService.insertProductCombo(productCombo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductCombo> updateProductCombo(@PathVariable long id,@Valid @RequestBody ProductCombo productCombo){
        ProductCombo updateProductCombo = iProductComboService.updateProductCombo(id, productCombo);
        return ResponseEntity.ok(updateProductCombo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProductCombo(@PathVariable long id){
        iProductComboService.deleteProductCombo(id);
        return ResponseEntity.ok("Delete product combo success!");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<ProductCombo>> getProductComboByID(@PathVariable long id){
        Optional<ProductCombo> productCombo = iProductComboService.getProductComboByID(id);
        return  ResponseEntity.ok(productCombo);
    }
}

