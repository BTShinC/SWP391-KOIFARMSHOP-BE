package com.example.SWP391_KOIFARMSHOP_BE.service;

import com.example.SWP391_KOIFARMSHOP_BE.exception.EntityNotFoundException;
import com.example.SWP391_KOIFARMSHOP_BE.model.OrderRequest;
import com.example.SWP391_KOIFARMSHOP_BE.model.OrderResponse;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.Account;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.Orders;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.Product;
import com.example.SWP391_KOIFARMSHOP_BE.repository.IAccountRepository;
import com.example.SWP391_KOIFARMSHOP_BE.repository.IOrdersRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public OrderResponse createOrder(OrderRequest orderRequest) {
        String nextId = generateNextOrderId();
        Orders order = modelMapper.map(orderRequest, Orders.class);
        order.setOrderID(nextId); // Set Order ID thủ công, bỏ qua ánh xạ từ request

        // Xác thực Account ID
        Account account = iAccountRepository.findById(orderRequest.getAccountId())
                .orElseThrow(() -> new EntityNotFoundException("Account with ID " + orderRequest.getAccountId() + " not found"));

        order.setAccount(account);
        order.setFeedback(null);  // Nếu cần xử lý feedback, có thể thêm logic tại đây
        order.setPayment(null);
        Orders savedOrder = iOrdersRepository.save(order);
        return modelMapper.map(savedOrder, OrderResponse.class);
    }


    private String generateNextOrderId() {
        Orders lastOrder = iOrdersRepository.findTopByOrderByOrderIDDesc();
        if (lastOrder != null) {
            String lastId = lastOrder.getOrderID();
            int idNumber = Integer.parseInt(lastId.substring(1));
            String nextId = String.format("O%03d", idNumber + 1);
            return nextId;
        } else {
            return "O001";
        }
    }

    // Get All Orders
    public List<OrderResponse> getAllOrders() {
        return iOrdersRepository.findAll().stream()
                .map(order -> modelMapper.map(order, OrderResponse.class))
                .collect(Collectors.toList());
    }

    // Get Order by ID
    public OrderResponse getOrderById(String orderId) {
        Orders order = iOrdersRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Orders with ID " + orderId + " not found"));
        return modelMapper.map(order, OrderResponse.class);
    }

    // Update Order
    public OrderResponse updateOrder(String orderId, OrderRequest orderRequest) {
        Orders existingOrder = iOrdersRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Orders with ID " + orderId + " not found"));

        // Cập nhật thông tin đơn hàng
        existingOrder.setStatus(orderRequest.getStatus());
        existingOrder.setTotal(orderRequest.getTotal());
        existingOrder.setDate(orderRequest.getDate());
        existingOrder.setDescription(orderRequest.getDescription());

        // Cập nhật thông tin Account
        Account account = iAccountRepository.findById(orderRequest.getAccountId())
                .orElseThrow(() -> new EntityNotFoundException("Account with ID " + orderRequest.getAccountId() + " not found"));
        existingOrder.setAccount(account);

        Orders updatedOrder = iOrdersRepository.save(existingOrder);
        return modelMapper.map(updatedOrder, OrderResponse.class);
    }

    // Delete Order
    public void deleteOrder(String orderId) {
        Orders order = iOrdersRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Orders with ID " + orderId + " not found"));
        iOrdersRepository.delete(order);
    }
}
