package com.example.SWP391_KOIFARMSHOP_BE.service;

import com.example.SWP391_KOIFARMSHOP_BE.pojo.Product;
import com.example.SWP391_KOIFARMSHOP_BE.repository.IProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IProductService{
    @Autowired
    private IProductRepository iProductRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public List<Product> getAllProduct() {
        return iProductRepository.findAll();
    }

    @Override
    public Product insertProduct(Product product) {
        return iProductRepository.save(product);
    }

    @Override
    public Product updateProduct(long productID, Product product) {
        Product p = iProductRepository.getById(productID);
        if(p != null){
            p.setBreed(product.getBreed());
            p.setSize(product.getSize());
            p.setSex(product.getSex());
            p.setHealthStatus(product.getHealthStatus());
            p.setPersonalityTrait(product.getPersonalityTrait());
            p.setOrigin(product.getOrigin());
            p.setDesciption(product.getDesciption());
            p.setImage(product.getImage());
            p.setPrice(product.getPrice());
            p.setCertificate(product.getCertificate());
            p.setType(product.getType());
            p.setQuantity(product.getQuantity());
            p.setStatus(product.getStatus());
            p.setDesiredPrice(product.getDesiredPrice());
            p.setConsignmentType(product.getConsignmentType());
            return iProductRepository.save(p);
        }
        return null;
    }

    @Override
    public void deleteProduct(long productID) {
        iProductRepository.deleteById(productID);
    }

    @Override
    public Optional<Product> getProductByID(long productID) {
        return iProductRepository.findById(productID);
    }
}
