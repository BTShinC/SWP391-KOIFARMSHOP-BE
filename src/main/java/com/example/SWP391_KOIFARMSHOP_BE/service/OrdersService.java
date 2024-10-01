package com.example.SWP391_KOIFARMSHOP_BE.service;

import com.example.SWP391_KOIFARMSHOP_BE.exception.EntityNotFoundException;
import com.example.SWP391_KOIFARMSHOP_BE.model.OrdersRequest;
import com.example.SWP391_KOIFARMSHOP_BE.model.OrdersResponse;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.Account;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.Orders;
import com.example.SWP391_KOIFARMSHOP_BE.repository.IAccountRepository;
import com.example.SWP391_KOIFARMSHOP_BE.repository.IOrdersRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrdersService {

    @Autowired
    private IOrdersRepository iOrdersRepository;

    @Autowired
    private IAccountRepository iAccountRepository;

    @Autowired
    private ModelMapper modelMapper;

    // Create Order
    public OrdersResponse createOrder(OrdersRequest ordersRequest) {
        Orders order = modelMapper.map(ordersRequest, Orders.class);

        // Xác thực Account ID
        Account account = iAccountRepository.findById(ordersRequest.getAccountId())
                .orElseThrow(() -> new EntityNotFoundException("Account not found"));

        order.setAccount(account);

        Orders savedOrder = iOrdersRepository.save(order);
        return modelMapper.map(savedOrder, OrdersResponse.class);
    }

    // Get All Orders
    public List<OrdersResponse> getAllOrders() {
        List<Orders> ordersList = iOrdersRepository.findAll();
        return ordersList.stream()
                .map(order -> modelMapper.map(order, OrdersResponse.class))
                .collect(Collectors.toList());
    }

    // Get Order by ID
    public OrdersResponse getOrderById(Long orderId) {
        Orders order = iOrdersRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with ID: " + orderId));
        return modelMapper.map(order, OrdersResponse.class);
    }

    // Update Order
    public OrdersResponse updateOrder(Long orderId, OrdersRequest ordersRequest) {
        Orders existingOrder = iOrdersRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with ID: " + orderId));

        // Cập nhật thông tin đơn hàng
        existingOrder.setStatus(ordersRequest.getStatus());
        existingOrder.setTotal(ordersRequest.getTotal());
        existingOrder.setDate(ordersRequest.getDate());
        existingOrder.setDescription(ordersRequest.getDescription());

        // Kiểm tra và cập nhật thông tin Account
        Account account = iAccountRepository.findById(ordersRequest.getAccountId())
                .orElseThrow(() -> new EntityNotFoundException("Account not found"));
        existingOrder.setAccount(account);

        Orders updatedOrder = iOrdersRepository.save(existingOrder);
        return modelMapper.map(updatedOrder, OrdersResponse.class);
    }

    // Delete Order
    public void deleteOrder(Long orderId) {
        Orders order = iOrdersRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with ID: " + orderId));

        iOrdersRepository.delete(order);
    }
}
