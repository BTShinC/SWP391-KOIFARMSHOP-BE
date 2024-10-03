package com.example.SWP391_KOIFARMSHOP_BE.service;

import com.example.SWP391_KOIFARMSHOP_BE.exception.EntityNotFoundException;
import com.example.SWP391_KOIFARMSHOP_BE.model.*;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.ProductCombo;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.Role;
import com.example.SWP391_KOIFARMSHOP_BE.repository.IProductComboRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class ProductComboService {
    @Autowired
    IProductComboRepository iProductComboRepository;

    @Autowired
    ModelMapper modelMapper;

    public ProductComboResponse createProductCombo(ProductComboRequest productComboRequest) {

        try {
            // Kiểm tra xem Product đã tồn tại chưa
            Optional<ProductCombo> existingProductCombo =  iProductComboRepository.findByName(productComboRequest.getName());

            if (existingProductCombo.isPresent()) {
                // Nếu Role đã tồn tại, trả về thông báo lỗi
                throw new IllegalArgumentException("Product with name '" + productComboRequest.getName() + "' already exists.");
            }

            // Nếu Role chưa tồn tại, tiếp tục lưu nó
            ProductCombo productCombo = modelMapper.map(productComboRequest, ProductCombo.class);
            System.out.println(productCombo);

            productCombo.setConsignment(null);
            productCombo.setCarePackage(null);
            productCombo.setOrdersdetail(null);
            System.out.println();
            ProductCombo savedProductCombo = iProductComboRepository.save(productCombo);

            return modelMapper.map(savedProductCombo, ProductComboResponse.class);
        } catch (IllegalArgumentException e) {
            // Trả về lỗi do trùng Product
            System.err.println("Error: " + e.getMessage());
            throw new IllegalArgumentException(e.getMessage());
        } catch (Exception e) {
            // Xử lý các lỗi khác
            System.err.println("Error: " + e.getMessage());
            throw new EntityNotFoundException("Error when trying to create ProductCombo.");

        }

    }













//    @Override
//    public List<ProductCombo> getAllProductCombo() {
//        return iProductComboRepository.findAll();
//    }
//
//    @Override
//    public ProductCombo insertProductCombo(ProductCombo productCombo) {
//        return iProductComboRepository.save(productCombo);
//    }
//
//    @Override
//    public ProductCombo updateProductCombo(long productComboID, ProductCombo productCombo) {
//        ProductCombo pc = iProductComboRepository.getById(productComboID);
//        if(pc != null){
//            pc.setSize(productCombo.getSize());
//            pc.setBreed(productCombo.getBreed());
//            pc.setHealthStatus(productCombo.getHealthStatus());
//            pc.setQuantity(productCombo.getQuantity());
//            pc.setDesciption(productCombo.getDesciption());
//            pc.setImage(productCombo.getImage());
//            pc.setPrice(productCombo.getPrice());
//            pc.setConsignmentType(productCombo.getConsignmentType());
//            pc.setDesiredPrice(productCombo.getDesiredPrice());
//            pc.setType(productCombo.getType());
//            return iProductComboRepository.save(pc);
//        }
//        return null;
//    }
//
//    @Override
//    public void deleteProductCombo(long productComboID) {
//        iProductComboRepository.deleteById(productComboID);
//    }
//
//    @Override
//    public Optional<ProductCombo> getProductComboByID(long productComboID) {
//        return iProductComboRepository.findById(productComboID);
//    }
}

