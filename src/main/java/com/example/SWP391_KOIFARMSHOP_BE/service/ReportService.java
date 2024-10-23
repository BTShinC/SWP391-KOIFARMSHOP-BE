package com.example.SWP391_KOIFARMSHOP_BE.service;

import com.example.SWP391_KOIFARMSHOP_BE.model.OrderReport;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.Orders;
import com.example.SWP391_KOIFARMSHOP_BE.repository.IOrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service

public class ReportService {

    @Autowired
    IOrdersRepository iOrdersRepository;

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
    


}
