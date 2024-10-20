package com.example.SWP391_KOIFARMSHOP_BE.service;

import com.example.SWP391_KOIFARMSHOP_BE.exception.EntityNotFoundException;
import com.example.SWP391_KOIFARMSHOP_BE.model.OrdersDetailResponse;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.Orders;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.OrdersDetail;
import com.example.SWP391_KOIFARMSHOP_BE.repository.IOrdersDetailRepository;
import com.example.SWP391_KOIFARMSHOP_BE.repository.IOrdersRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrdersDetailService {

    @Autowired
    private IOrdersDetailRepository ordersDetailRepository;

    @Autowired
    private IOrdersRepository ordersRepository;

    @Autowired
    private ModelMapper modelMapper;

    // Lấy tất cả OrdersDetail
    public List<OrdersDetailResponse> getAllOrdersDetails() {
        List<OrdersDetail> ordersDetails = ordersDetailRepository.findAll();
        return ordersDetails.stream()
                .map(ordersDetail -> modelMapper.map(ordersDetail, OrdersDetailResponse.class))
                .collect(Collectors.toList());
    }

    // Lấy tất cả OrdersDetail theo OrderID
    public List<OrdersDetailResponse> getOrdersDetailsByOrderId(String orderId) {
        // Kiểm tra xem Order có tồn tại không
        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order with ID " + orderId + " not found"));

        // Lấy danh sách OrdersDetail theo OrderID
        List<OrdersDetail> ordersDetails = ordersDetailRepository.findByOrders(order);
        return ordersDetails.stream()
                .map(ordersDetail -> modelMapper.map(ordersDetail, OrdersDetailResponse.class))
                .collect(Collectors.toList());
    }
}
