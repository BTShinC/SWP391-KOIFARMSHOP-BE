package com.example.SWP391_KOIFARMSHOP_BE.service;

import com.example.SWP391_KOIFARMSHOP_BE.exception.EntityNotFoundException;
import com.example.SWP391_KOIFARMSHOP_BE.model.AccountResponse;
import com.example.SWP391_KOIFARMSHOP_BE.model.ConsignmentResponse;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.*;
import com.example.SWP391_KOIFARMSHOP_BE.repository.IAccountRepository;
import com.example.SWP391_KOIFARMSHOP_BE.repository.IConsignmentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RefundService {

   @Autowired
   IAccountRepository accountRepository;
   @Autowired
   IConsignmentRepository consignmentRepository;
   @Autowired
   ModelMapper modelMapper;
   @Autowired
   AccountService accountService;

    public String refundProduct(String consignmentID) {
        Consignment consignment = consignmentRepository.findByconsignmentID(consignmentID);

        if (consignment == null) {
            throw new EntityNotFoundException("Consignment not found for ID: " + consignmentID);
        }


        if (consignment.getStatus().equalsIgnoreCase("Chưa hoàn tiền")) {
            Product product = consignment.getProduct();
            ProductCombo productCombo = consignment.getProductCombo();

            double refundAmount = 0;
            if (product != null) {
                refundAmount = product.getPrice() * 0.8;
            } else if (productCombo != null) {
                refundAmount = productCombo.getPrice() * 0.8;
            }

            if (refundAmount == 0) {
                throw new IllegalArgumentException("No valid product or combo found for refund.");
            }

            String accountResponse = accountService.updateAccountBalancRefund(consignment.getAccountID(), refundAmount);

            consignment.setStatus("Hoàn tất");
            consignmentRepository.save(consignment);
            return "Refund successful! " + accountResponse;

        } else {
            throw new IllegalArgumentException("Consignment is not sold, cannot process refund.");
        }
    }

    public String refundProducts(String consignmentID) {
        Consignment consignment = consignmentRepository.findByconsignmentID(consignmentID);

        if (consignment == null) {
            throw new EntityNotFoundException("Consignment not found for ID: " + consignmentID);
        }

        if (consignment.getStatus().equalsIgnoreCase("Chờ xác nhận")) {


            double refundAmount;
             refundAmount = consignment.getTotal();

            String accountResponse = accountService.updateAccountBalancRefund(consignment.getAccountID(), refundAmount);

            consignment.setStatus("Đã hủy");
            consignmentRepository.save(consignment);
            return "Refund successful! " + accountResponse;

        } else {
            throw new IllegalArgumentException("Consignment is not sold, cannot process refund.");
        }
    }

}
