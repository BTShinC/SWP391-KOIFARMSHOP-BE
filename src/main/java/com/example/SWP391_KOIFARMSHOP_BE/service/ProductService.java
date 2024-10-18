package com.example.SWP391_KOIFARMSHOP_BE.service;

import com.example.SWP391_KOIFARMSHOP_BE.exception.EntityNotFoundException;
import com.example.SWP391_KOIFARMSHOP_BE.model.ProductRequest;
import com.example.SWP391_KOIFARMSHOP_BE.model.ProductResponse;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.Account;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.CarePackage;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.Product;
import com.example.SWP391_KOIFARMSHOP_BE.repository.ICarePackageRepository;
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
    private ICarePackageRepository iCarePackageRepository;

    @Autowired
    private ModelMapper modelMapper;

    // Tạo sản phẩm mới
    public ProductResponse createProduct(ProductRequest productRequest) {
        // Kiểm tra sản phẩm đã tồn tại dựa trên tên sản phẩm
        Optional<Product> existingProduct = iProductRepository.findByProductName(productRequest.getProductName());
        if (existingProduct.isPresent()) {
            throw new IllegalArgumentException("Product with name '" + productRequest.getProductName() + "' already exists.");
        }

        // Tạo ID mới cho sản phẩm
        String nextId = generateNextProductId();

        // Ánh xạ từ productRequest sang product entity
        Product product = modelMapper.map(productRequest, Product.class);
        product.setProductID(nextId);

        // Ánh xạ carePackageID nếu có trong request
        if (productRequest.getCarePackageID() != null) {
            CarePackage carePackage = iCarePackageRepository.findById(productRequest.getCarePackageID())
                    .orElseThrow(() -> new EntityNotFoundException("CarePackage with ID " + productRequest.getCarePackageID() + " not found"));
            product.setCarePackage(carePackage);
        } else {
            product.setCarePackage(null);
        }

        product.setConsignment(null);
        product.setOrdersdetail(null);

        // Lưu sản phẩm
        Product savedProduct = iProductRepository.save(product);

        // Trả về response
        return modelMapper.map(savedProduct, ProductResponse.class);
    }

    private String generateNextProductId() {
        Product lastProduct = iProductRepository.findTopByOrderByProductIDDesc();
        if (lastProduct != null) {
            String lastId = lastProduct.getProductID();
            int idNumber = Integer.parseInt(lastId.substring(1));
            String nextId = String.format("P%03d", idNumber + 1);
            return nextId;
        } else {
            return "P001";
        }
    }

    // Lấy tất cả sản phẩm
    public List<ProductResponse> getAllProducts() {
        List<Product> products = iProductRepository.findAll();
        return products.stream()
                .map(product -> modelMapper.map(product, ProductResponse.class))
                .collect(Collectors.toList());
    }

    // Lấy sản phẩm theo ID
    public ProductResponse getProductById(String productId) {
        Product product = iProductRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product with ID " + productId + " not found"));
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
    public ProductResponse updateProduct(String productId, ProductRequest productRequest) {
        // Lấy product hiện có từ cơ sở dữ liệu
        Product product = iProductRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product with ID " + productId + " not found"));

        // Nếu carePackageID trong request không null, tìm carePackage hiện tại
        if (productRequest.getCarePackageID() != null) {
            CarePackage carePackage = iCarePackageRepository.findById(productRequest.getCarePackageID())
                    .orElseThrow(() -> new EntityNotFoundException("CarePackage with ID " + productRequest.getCarePackageID() + " not found"));

            // Chỉ gán lại CarePackage mà không tạo thực thể mới
            product.setCarePackage(carePackage);
        }

        // Cập nhật thông tin khác của product (không bao gồm CarePackage)
        product.setProductName(productRequest.getProductName());
        product.setBreed(productRequest.getBreed());
        product.setSize(productRequest.getSize());
        product.setSex(productRequest.getSex());
        product.setHealthStatus(productRequest.getHealthStatus());
        product.setPersonalityTrait(productRequest.getPersonalityTrait());
        product.setOrigin(productRequest.getOrigin());
        product.setDescription(productRequest.getDescription());
        product.setImage(productRequest.getImage());
        product.setPrice(productRequest.getPrice());
        product.setCertificate(productRequest.getCertificate());
        product.setType(productRequest.getType());
        product.setQuantity(productRequest.getQuantity());
        product.setStatus(productRequest.getStatus());
        product.setDesiredPrice(productRequest.getDesiredPrice());
        product.setConsignmentType(productRequest.getConsignmentType());
        product.setAge(productRequest.getAge());

        // Lưu product đã cập nhật
        Product updatedProduct = iProductRepository.save(product);
        return modelMapper.map(updatedProduct, ProductResponse.class);
    }


    // Xóa sản phẩm
    public void deleteProduct(String productId) {
        Product product = iProductRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product with ID " + productId + " not found"));
        iProductRepository.delete(product);
    }

}
