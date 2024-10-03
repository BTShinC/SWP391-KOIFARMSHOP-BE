package com.example.SWP391_KOIFARMSHOP_BE.service;

import com.example.SWP391_KOIFARMSHOP_BE.exception.EntityNotFoundException;
import com.example.SWP391_KOIFARMSHOP_BE.model.ProductRequest;
import com.example.SWP391_KOIFARMSHOP_BE.model.ProductResponse;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.Product;
import com.example.SWP391_KOIFARMSHOP_BE.repository.IProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

public class ProductService {

    @Autowired
    private IProductRepository iProductRepository;

    @Autowired
    private ModelMapper modelMapper;

    // Tạo sản phẩm mới
    public ProductResponse createProduct(ProductRequest productRequest) {
        Optional<Product> existingProduct = iProductRepository.findByProductName(productRequest.getProductName());

        if (existingProduct.isPresent()) {
            throw new IllegalArgumentException("Product with name '" + productRequest.getProductName() + "' already exists.");
        }
        Product product = modelMapper.map(productRequest, Product.class);
        product.setConsignment(null);
        product.setCarePackage(null);
        product.setOrdersdetail(null);
        Product savedProduct = iProductRepository.save(product);
        return modelMapper.map(savedProduct, ProductResponse.class);
    }

    // Lấy tất cả sản phẩm
    public List<ProductResponse> getAllProducts() {
        List<Product> products = iProductRepository.findAll();
        return products.stream()
                .map(product -> modelMapper.map(product, ProductResponse.class))
                .collect(Collectors.toList());
    }

    // Lấy sản phẩm theo ID
    public ProductResponse getProductById(Long productId) {
        Product product = iProductRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
        return modelMapper.map(product, ProductResponse.class);
    }
    // Lấy sản phẩm theo breed
    public ProductResponse getProductByBreed(String breed){
        Product product = iProductRepository.findByBreed(breed);
        if (product == null) {
            throw new IllegalArgumentException("Product with name '" + breed + "' already exists.");
        }
        return modelMapper.map(product, ProductResponse.class);
    }

    // Cập nhật sản phẩm
    public ProductResponse updateProduct(Long productId, ProductRequest productRequest) {
        Product product = iProductRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        // Cập nhật thông tin sản phẩm
        modelMapper.map(productRequest, product);
        product.setConsignment(null);
        product.setCarePackage(null);
        product.setOrdersdetail(null);
        Product updatedProduct = iProductRepository.save(product);
        return modelMapper.map(updatedProduct, ProductResponse.class);
    }

    // Xóa sản phẩm
    public void deleteProduct(Long productId) {
        Product product = iProductRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
        iProductRepository.delete(product);

    }












//    @Override
//    public List<Product> getAllProduct() {
//        return iProductRepository.findAll();
//    }
//
//    @Override
//    public Product insertProduct(Product product) {
//        return iProductRepository.save(product);
//    }
//
//
//    @Override
//    public Product updateProduct(long productID, Product product) {
//        Product p = iProductRepository.getById(productID);
//        if(p != null){
//            p.setBreed(product.getBreed());
//            p.setSize(product.getSize());
//            p.setSex(product.getSex());
//            p.setHealthStatus(product.getHealthStatus());
//            p.setPersonalityTrait(product.getPersonalityTrait());
//            p.setOrigin(product.getOrigin());
//            p.setDesciption(product.getDesciption());
//            p.setImage(product.getImage());
//            p.setPrice(product.getPrice());
//            p.setCertificate(product.getCertificate());
//            p.setType(product.getType());
//            p.setQuantity(product.getQuantity());
//            p.setStatus(product.getStatus());
//            p.setDesiredPrice(product.getDesiredPrice());
//            p.setConsignmentType(product.getConsignmentType());
//            return iProductRepository.save(p);
//        }
//        return null;
//    }
//
//    @Override
//    public void deleteProduct(long productID) {
//        iProductRepository.deleteById(productID);
//    }
//
//    @Override
//    public Optional<Product> getProductByID(long productID) {
//        return iProductRepository.findById(productID);
//    }
}
