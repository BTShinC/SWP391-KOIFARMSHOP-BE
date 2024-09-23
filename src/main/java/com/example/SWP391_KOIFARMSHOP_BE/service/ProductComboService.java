package com.example.SWP391_KOIFARMSHOP_BE.service;

import com.example.SWP391_KOIFARMSHOP_BE.pojo.ProductCombo;
import com.example.SWP391_KOIFARMSHOP_BE.repository.IProductComboRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductComboService implements IProductComboService{
    @Autowired
    private IProductComboRepository iProductComboRepository;
    @Override
    public List<ProductCombo> getAllProductCombo() {
        return iProductComboRepository.findAll();
    }

    @Override
    public ProductCombo insertProductCombo(ProductCombo productCombo) {
        return iProductComboRepository.save(productCombo);
    }

    @Override
    public ProductCombo updateProductCombo(long productComboID, ProductCombo productCombo) {
        ProductCombo pc = iProductComboRepository.getById(productComboID);
        if(pc != null){
            pc.setSize(productCombo.getSize());
            pc.setBreed(productCombo.getBreed());
            pc.setHealthStatus(productCombo.getHealthStatus());
            pc.setQuantity(productCombo.getQuantity());
            pc.setDesciption(productCombo.getDesciption());
            pc.setImage(productCombo.getImage());
            pc.setPrice(productCombo.getPrice());
            pc.setConsignmentType(productCombo.getConsignmentType());
            pc.setDesiredPrice(productCombo.getDesiredPrice());
            pc.setType(productCombo.getType());
            return iProductComboRepository.save(pc);
        }
        return null;
    }

    @Override
    public void deleteProductCombo(long productComboID) {
        iProductComboRepository.deleteById(productComboID);
    }

    @Override
    public Optional<ProductCombo> getProductComboByID(long productComboID) {
        return iProductComboRepository.findById(productComboID);
    }
}

