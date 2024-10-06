package com.example.SWP391_KOIFARMSHOP_BE.service;


import com.example.SWP391_KOIFARMSHOP_BE.pojo.OrdersDetail;
import com.example.SWP391_KOIFARMSHOP_BE.repository.IOrdersDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrdersDetailService {
    @Autowired
    private IOrdersDetailRepository iOrdersDetailRepository;

    public List<OrdersDetail> getAllOrdersDetail() {
        return iOrdersDetailRepository.findAll();
    }


    public OrdersDetail insertOrdersDetail(OrdersDetail ordersDetail) {
        return iOrdersDetailRepository.save(ordersDetail);
    }


    public OrdersDetail updateOrdersDetail(String ordersDetailID, OrdersDetail ordersDetail) {
        OrdersDetail od = iOrdersDetailRepository.getById(ordersDetailID);
        return null;
    }


    public void deleteOrdersDetail(String ordersDetailID) {
        iOrdersDetailRepository.deleteById(ordersDetailID);
    }


    public Optional<OrdersDetail> getOrdersDetailByID(String ordersDetailID) {
        return iOrdersDetailRepository.findById(ordersDetailID);
    }
}
