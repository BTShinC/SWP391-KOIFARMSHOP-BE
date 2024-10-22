package com.example.SWP391_KOIFARMSHOP_BE.service;

import com.example.SWP391_KOIFARMSHOP_BE.exception.EntityNotFoundException;
import com.example.SWP391_KOIFARMSHOP_BE.model.ConsignmentRequest;
import com.example.SWP391_KOIFARMSHOP_BE.model.ConsignmentResponse;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.Account;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.Consignment;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.Product;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.ProductCombo;
import com.example.SWP391_KOIFARMSHOP_BE.repository.IAccountRepository;
import com.example.SWP391_KOIFARMSHOP_BE.repository.IConsignmentRepository;
import com.example.SWP391_KOIFARMSHOP_BE.repository.IProductComboRepository;
import com.example.SWP391_KOIFARMSHOP_BE.repository.IProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConsignmentService {

    @Autowired
    private IConsignmentRepository consignmentRepository;

    @Autowired
    private IProductRepository productRepository;

    @Autowired
    private IProductComboRepository productComboRepository;
    @Autowired
    private IAccountRepository accountRepository;

    @Autowired
    private ModelMapper modelMapper;


    // Tạo ID mới cho Consignment
    private String generateNextConsignmentID() {
        Consignment lastConsignment = consignmentRepository.findTopByOrderByConsignmentIDDesc();
        if (lastConsignment != null) {
            String lastId = lastConsignment.getConsignmentID();
            int idNumber = Integer.parseInt(lastId.substring(2));  // Bỏ tiền tố CO
            return String.format("CO%03d", idNumber + 1);
        } else {
            return "CO001";  // ID đầu tiên
        }
    }

    // Tạo consignment mới
    public ConsignmentResponse createConsignment(ConsignmentRequest consignmentRequest) {
        Account account = accountRepository.findById(consignmentRequest.getAccountID())
                .orElseThrow(() -> new EntityNotFoundException("Account with ID " +consignmentRequest.getAccountID() + " not found"));

        Consignment consignment = new Consignment();
        consignment.setConsignmentID(generateNextConsignmentID());
        consignment.setConsignmentDate(consignmentRequest.getConsignmentDate());
        consignment.setSaleDate(consignmentRequest.getSaleDate());
        consignment.setSalePrice(consignmentRequest.getSalePrice());
        consignment.setDateReceived(consignmentRequest.getDateReceived());
        consignment.setDateExpiration(consignmentRequest.getDateExpiration());
        consignment.setStatus(consignmentRequest.getStatus());
        consignment.setAccountID(consignmentRequest.getAccountID());
        consignment.setToltal(consignmentRequest.getToltal());

        // Kiểm tra nếu Product hoặc ProductCombo tồn tại và có type là "Consignment"
        if (consignmentRequest.getProductID() != null && !consignmentRequest.getProductID().isEmpty()) {
            Product product = productRepository.findById(consignmentRequest.getProductID())
                    .orElseThrow(() -> new EntityNotFoundException("Product with ID " + consignmentRequest.getProductID() + " not found"));

            // Kiểm tra type của Product phải là "Consignment"
            if (!"Ký gửi".equalsIgnoreCase(product.getType())) {
                throw new IllegalArgumentException("Product is not for consignment. Type must be 'Consignment'.");
            }

            consignment.setProduct(product);

        } else if (consignmentRequest.getProductComboID() != null && !consignmentRequest.getProductComboID().isEmpty()) {
            ProductCombo productCombo = productComboRepository.findById(consignmentRequest.getProductComboID())
                    .orElseThrow(() -> new EntityNotFoundException("ProductCombo with ID " + consignmentRequest.getProductComboID() + " not found"));

            // Kiểm tra type của ProductCombo phải là "Consignment"
            if (!"Ký gửi".equalsIgnoreCase(productCombo.getType())) {
                throw new IllegalArgumentException("ProductCombo is not for consignment. Type must be 'Consignment'.");
            }

            consignment.setProductCombo(productCombo);

        } else {
            throw new IllegalArgumentException("Either productID or productComboID must be provided.");
        }

        // Lưu consignment vào cơ sở dữ liệu
        Consignment savedConsignment = consignmentRepository.save(consignment);
        return modelMapper.map(savedConsignment, ConsignmentResponse.class);
    }


    // Lấy tất cả consignment
    public List<ConsignmentResponse> getAllConsignments() {
        return consignmentRepository.findAll().stream()
                .map(consignment -> modelMapper.map(consignment, ConsignmentResponse.class))
                .collect(Collectors.toList());
    }

    // Lấy consignment theo ID
    public ConsignmentResponse getConsignmentById(String consignmentID) {
        Consignment consignment = consignmentRepository.findById(consignmentID)
                .orElseThrow(() -> new EntityNotFoundException("Consignment with ID " + consignmentID + " not found"));
        return modelMapper.map(consignment, ConsignmentResponse.class);
    }

    // Cập nhật consignment
    public ConsignmentResponse updateConsignment(String consignmentID, ConsignmentRequest consignmentRequest) {
        Consignment consignment = consignmentRepository.findById(consignmentID)
                .orElseThrow(() -> new EntityNotFoundException("Consignment with ID " + consignmentID + " not found"));

        consignment.setConsignmentDate(consignmentRequest.getConsignmentDate());
        consignment.setSaleDate(consignmentRequest.getSaleDate());
        consignment.setSalePrice(consignmentRequest.getSalePrice());
        consignment.setDateReceived(consignmentRequest.getDateReceived());
        consignment.setDateExpiration(consignmentRequest.getDateExpiration());
        consignment.setStatus(consignmentRequest.getStatus());

        Consignment updatedConsignment = consignmentRepository.save(consignment);
        return modelMapper.map(updatedConsignment, ConsignmentResponse.class);
    }
    // Lấy sản phẩm theo accoutID
    public List<ConsignmentResponse> getConsignmentsByAccountID(String accountID) {
        Account account = accountRepository.findById(accountID)
                .orElseThrow(() -> new EntityNotFoundException("Account with ID " + accountID + " not found"));

        List<Consignment> consignments = consignmentRepository.findByAccountID(accountID);

        return consignments.stream()
                .map(consignment -> modelMapper.map(consignment, ConsignmentResponse.class))
                .collect(Collectors.toList());
    }

    // Xóa consignment
    public void deleteConsignment(String consignmentID) {
        Consignment consignment = consignmentRepository.findById(consignmentID)
                .orElseThrow(() -> new EntityNotFoundException("Consignment with ID " + consignmentID + " not found"));
        consignmentRepository.delete(consignment);
    }
}
