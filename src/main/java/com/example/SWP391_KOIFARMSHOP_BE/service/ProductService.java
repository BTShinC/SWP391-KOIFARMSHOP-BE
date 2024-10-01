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

@Service
public class ProductService /*implements IProductService*/{
    @Autowired
    private IProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;

    // Thêm mới sản phẩm
    public ProductResponse createProduct(ProductRequest productRequest) {
      // try {
           Product product = modelMapper.map(productRequest, Product.class);
           Product savedProduct = productRepository.save(product);
           return modelMapper.map(savedProduct, ProductResponse.class);
//       }catch(Exception e){
//           System.err.println("Error : " + e.getMessage());
//           throw new EntityNotFoundException("Error when to Create ");
//       }
    }

    // Lấy sản phẩm theo ID
    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        return modelMapper.map(product, ProductResponse.class);
    }

    // Cập nhật sản phẩm
    public ProductResponse updateProduct(Long id, ProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        product.setStatus(request.getStatus());
        product.setPrice(request.getPrice());
        product.setConsignmentType(request.getConsignmentType());

        Product updatedProduct = productRepository.save(product);
        return modelMapper.map(updatedProduct, ProductResponse.class);
    }

    // Xóa sản phẩm
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        productRepository.delete(product);
    }

    // Lấy tất cả sản phẩm
    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(product -> modelMapper.map(product, ProductResponse.class))
                .collect(Collectors.toList());
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
