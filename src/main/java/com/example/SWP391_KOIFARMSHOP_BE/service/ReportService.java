package com.example.SWP391_KOIFARMSHOP_BE.service;

import com.example.SWP391_KOIFARMSHOP_BE.model.OrderReport;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.*;
import com.example.SWP391_KOIFARMSHOP_BE.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

public class ReportService {

    @Autowired
    IOrdersRepository iOrdersRepository;

    @Autowired
    IProductRepository  iProductRepository;

    @Autowired
    IProductComboRepository iProductComboRepository;
    @Autowired
    IAccountRepository iAccountRepository;

    @Autowired
    IRoleRepository iRoleRepository;

    public List<OrderReport> ordershop(){
        List<Orders> orders = iOrdersRepository.findBystatus(" Hoàn tất");
        return orders.stream()
                .map(order -> {
                    OrderReport report = new OrderReport();
                    report.setOrderID(order.getOrderID());
                    report.setStatus(order.getStatus());
                    report.setTotal(order.getTotal());
                    report.setDate(order.getDate());
                    return report;
                })
                .collect(Collectors.toList());
    }
    public String getcountproduct(){
        List<Product> products = iProductRepository.findAll();
        int count = 0;
       for(Product product : products ) {
           if ("Còn hàng".equals(product.getStatus())) {
              count++;
           }
       }
        return "Số lượng cá thể còn hàng: "+count;
    }
    public String getcountproductCombo(){
        List<ProductCombo> products = iProductComboRepository.findAll();
        int count = 0;
        for(ProductCombo productCombo : products ) {
            if ("Còn hàng".equals(productCombo.getStatus())) {
                count++;
            }
        }
        return "Số lượng lô còn hàng: "+count;
    }
    public String getall(){
        List<Product> products = iProductRepository.findAll();
        List<ProductCombo>productCombos = iProductComboRepository.findAll();
        int count = 0;
        for(Product product : products ) {
                count++;
        }
        for(ProductCombo productCombo : productCombos ) {
                count++;
        }
        return "Sản phẩm còn hàng trong hệ thống: " +count;
    }
    public String getallaccount(){
        List<Account> accounts = iAccountRepository.findAll();
        int count = 0;
        for(Account account : accounts ) {
            count++;
        }
        return "Tất cả  tài khoản trong hệ thống : " +count;
    }
    public String getaccountcustomer(){
        Optional<Role> roleOptional = iRoleRepository.findByRoleName("Customer");
        if (roleOptional.isPresent()) {
            Role role = roleOptional.get();
            List<Account> accounts = iAccountRepository.findByRole(role);
            int count = accounts.size(); // Đếm số lượng tài khoản
            return "Số lượng tài khoản có vai trò khách hàng: " + count;
        } else {
            return "Vai trò khách hàng không tồn tại.";
        }
    }
    public List<String> getTop5BestSellingBreeds() {

        List<Product> products = iProductRepository.findAll();
        List<ProductCombo> productCombos = iProductComboRepository.findAll();


        List<String> allBreedsSold = new ArrayList<>();

        for (Product product : products) {
            if ("Đã bán".equals(product.getStatus())) {
                allBreedsSold.add(product.getBreed());
            }
        }

        for (ProductCombo productCombo : productCombos) {
            if ("Đã bán".equals(productCombo.getStatus())) {
                allBreedsSold.add(productCombo.getBreed());
            }
        }


        Map<String, Long> breedCount = allBreedsSold.stream()
                .collect(Collectors.groupingBy(breed -> breed, Collectors.counting()));


        return breedCount.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(5)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }




}
