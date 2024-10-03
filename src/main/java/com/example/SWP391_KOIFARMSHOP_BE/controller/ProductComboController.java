package com.example.SWP391_KOIFARMSHOP_BE.controller;

import com.example.SWP391_KOIFARMSHOP_BE.model.ProductComboRequest;
import com.example.SWP391_KOIFARMSHOP_BE.model.ProductComboResponse;
import com.example.SWP391_KOIFARMSHOP_BE.service.ProductComboService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/productCombo")
public class ProductComboController {
    @Autowired
    ProductComboService productComboService;

    @PostMapping("Create")
    public ResponseEntity CreatProduct(@Valid @RequestBody ProductComboRequest productComboRequest) {
        ProductComboResponse newProductCombo = productComboService.createProductCombo(productComboRequest);
        return ResponseEntity.ok(newProductCombo);
    }

//    @Autowired
//    private IProductComboService iProductComboService;
//    @GetMapping("/")
//    public ResponseEntity<List<ProductCombo>> fetchALlProductCombo(){
//        return ResponseEntity.ok(iProductComboService.getAllProductCombo());
//    }
//    @PostMapping("/")
//    @ResponseStatus (HttpStatus.CREATED)
//    public ProductCombo saveProductCombo(@RequestBody ProductCombo productCombo){
//        return iProductComboService.insertProductCombo(productCombo);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<ProductCombo> updateProductCombo(@PathVariable long id, @RequestBody ProductCombo productCombo){
//        ProductCombo updateProductCombo = iProductComboService.updateProductCombo(id, productCombo);
//        return ResponseEntity.ok(updateProductCombo);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<String> deleteProductCombo(@PathVariable long id){
//        iProductComboService.deleteProductCombo(id);
//        return ResponseEntity.ok("Delete product combo success!");
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Optional<ProductCombo>> getProductComboByID(@PathVariable long id){
//        Optional<ProductCombo> productCombo = iProductComboService.getProductComboByID(id);
//        return  ResponseEntity.ok(productCombo);
//    }
}

