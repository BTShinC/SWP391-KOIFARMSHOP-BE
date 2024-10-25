package com.example.SWP391_KOIFARMSHOP_BE.service;

import com.example.SWP391_KOIFARMSHOP_BE.exception.EntityNotFoundException;
import com.example.SWP391_KOIFARMSHOP_BE.model.*;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.CarePackage;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.Product;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.ProductCombo;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.Role;
import com.example.SWP391_KOIFARMSHOP_BE.repository.ICarePackageRepository;
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
    private IProductComboRepository iProductComboRepository;
    @Autowired
    private ICarePackageRepository iCarePackageRepository;


    @Autowired
    private ModelMapper modelMapper;

    // Tạo sản phẩm mới
    public ProductComboResponse createProductCombo(ProductComboRequest productComboRequest) {
        // Kiểm tra nếu combo đã tồn tại
        Optional<ProductCombo> existingCombo = iProductComboRepository.findByComboName(productComboRequest.getComboName());
        if (existingCombo.isPresent()) {
            throw new IllegalArgumentException("Product Combo with name '" + productComboRequest.getComboName() + "' already exists.");
        }

        // Tạo ID mới cho ProductCombo
        String nextId = generateNextProductComboId();

        // Ánh xạ từ request sang entity
        ProductCombo productCombo = modelMapper.map(productComboRequest, ProductCombo.class);
        productCombo.setProductComboID(nextId);

        // Kiểm tra và gán CarePackage nếu có carePackageID trong request
        if (productComboRequest.getCarePackageID() != null) {
            CarePackage carePackage = iCarePackageRepository.findById(productComboRequest.getCarePackageID())
                    .orElseThrow(() -> new EntityNotFoundException("CarePackage with ID " + productComboRequest.getCarePackageID() + " not found"));
            productCombo.setCarePackage(carePackage);
        } else {
            productCombo.setCarePackage(null);
        }

        productCombo.setConsignment(null);
        productCombo.setOrdersdetail(null);

        // Lưu ProductCombo
        ProductCombo savedProductCombo = iProductComboRepository.save(productCombo);
        return modelMapper.map(savedProductCombo, ProductComboResponse.class);
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
    public ProductComboResponse updateProductCombo(String productComboId, ProductComboRequest productComboRequest) {
        // Lấy ProductCombo hiện có từ database
        ProductCombo productCombo = iProductComboRepository.findById(productComboId)
                .orElseThrow(() -> new EntityNotFoundException("Product Combo with ID " + productComboId + " not found"));

        // Kiểm tra và cập nhật CarePackage nếu có carePackageID trong request
        if (productComboRequest.getCarePackageID() != null) {
            CarePackage carePackage = iCarePackageRepository.findById(productComboRequest.getCarePackageID())
                    .orElseThrow(() -> new EntityNotFoundException("CarePackage with ID " + productComboRequest.getCarePackageID() + " not found"));
            productCombo.setCarePackage(carePackage);
        } else {
            productCombo.setCarePackage(null);
        }

        // Cập nhật các trường khác
        productCombo.setComboName(productComboRequest.getComboName());
        productCombo.setBreed(productComboRequest.getBreed());
        productCombo.setSize(productComboRequest.getSize());
        productCombo.setHealthStatus(productComboRequest.getHealthStatus());
        productCombo.setDescription(productComboRequest.getDescription());
        productCombo.setImage(productComboRequest.getImage());
        productCombo.setImage1(productComboRequest.getImage1());
        productCombo.setImage2(productComboRequest.getImage2());
        productCombo.setPrice(productComboRequest.getPrice());
        productCombo.setQuantity(productComboRequest.getQuantity());
        productCombo.setConsignmentType(productComboRequest.getConsignmentType());
        productCombo.setDesiredPrice(productComboRequest.getDesiredPrice());
        productCombo.setType(productComboRequest.getType());
        productCombo.setStatus(productComboRequest.getStatus());

        // Lưu ProductCombo đã cập nhật
        ProductCombo updatedProductCombo = iProductComboRepository.save(productCombo);
        return modelMapper.map(updatedProductCombo, ProductComboResponse.class);
    }


    // Xóa sản phẩm
    public void deleteProduct(String productId) {
        ProductCombo product = iProductComboRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product combo with ID " + productId + " not found"));
        iProductComboRepository.delete(product);

    }

    // Get product combos by type "Trang trại"
    public List<ProductComboResponse> getProductCombosByFarmType() {
        List<ProductCombo> productCombos = iProductComboRepository.findByType("Trang trại");

        return productCombos.stream()
                .map(productCombo -> modelMapper.map(productCombo, ProductComboResponse.class))
                .collect(Collectors.toList());
    }
    // Get product combos by type "Ký gửi" and consignment type "Ký gửi để bán"
    public List<ProductComboResponse> getProductCombosForConsignmentSale() {
        List<ProductCombo> productCombos = iProductComboRepository.findByType("Ký gửi")
                .stream()
                .filter(productCombo -> "Ký gửi để bán".equals(productCombo.getConsignmentType()))
                .collect(Collectors.toList());

        return productCombos.stream()
                .map(productCombo -> modelMapper.map(productCombo, ProductComboResponse.class))
                .collect(Collectors.toList());
    }

}