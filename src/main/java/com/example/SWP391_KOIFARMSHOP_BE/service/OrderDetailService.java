package com.example.SWP391_KOIFARMSHOP_BE.service;


import com.example.SWP391_KOIFARMSHOP_BE.pojo.OrdersDetail;
import com.example.SWP391_KOIFARMSHOP_BE.repository.IOrdersDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailService implements IOrdersDetailService {
    @Autowired
    private IOrdersDetailRepository iOrdersDetailRepository;
    @Override
    public List<OrdersDetail> getAllOrdersDetail() {
        return iOrdersDetailRepository.findAll();
    }

    @Override
    public OrdersDetail insertOrdersDetail(OrdersDetail ordersDetail) {
        return iOrdersDetailRepository.save(ordersDetail);
    }

    @Override
    public OrdersDetail updateOrdersDetail(long ordersDetailID, OrdersDetail ordersDetail) {
        OrdersDetail od = iOrdersDetailRepository.getById(ordersDetailID);
        return null;
    }

    @Override
    public void deleteOrdersDetail(long ordersDetailID) {
        iOrdersDetailRepository.deleteById(ordersDetailID);
    }

    @Override
    public Optional<OrdersDetail> getOrdersDetailByID(long ordersDetailID) {
        return iOrdersDetailRepository.findById(ordersDetailID);
    }
}
