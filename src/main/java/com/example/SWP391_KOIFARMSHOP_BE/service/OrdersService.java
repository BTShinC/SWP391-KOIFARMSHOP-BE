package com.example.SWP391_KOIFARMSHOP_BE.service;

import com.example.SWP391_KOIFARMSHOP_BE.exception.EntityNotFoundException;
import com.example.SWP391_KOIFARMSHOP_BE.model.OrderRequest;
import com.example.SWP391_KOIFARMSHOP_BE.model.OrderResponse;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.*;
import com.example.SWP391_KOIFARMSHOP_BE.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrdersService {

    @Autowired
    private IOrdersRepository iOrdersRepository;

    @Autowired
    private IAccountRepository iAccountRepository;

    @Autowired
    private IProductRepository iProductRepository;

    @Autowired
    private IProductComboRepository iProductComboRepository;

    @Autowired
    private IOrdersDetailRepository iOrdersDetailRepository;

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


    public OrderResponse createOrderWithMultipleProducts(String accountId, List<String> productIds, List<String> productComboIds) {
        // 1. Kiểm tra Account
        Account account = iAccountRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException("Account with ID " + accountId + " not found"));

        // 2. Tạo Order mới
        Orders order = new Orders();
        String nextId = generateNextOrderId(); // Tạo ID mới
        order.setOrderID(nextId);
        order.setAccount(account);
        order.setDate(new Date()); // Thời gian hiện tại
        order.setStatus("Đang xử lý");
        order.setTotal(0); // Tổng tiền sẽ được tính sau

        // 3. Lưu Order trước khi tạo OrderDetail
        Orders savedOrder = iOrdersRepository.save(order);

        // 4. Tạo danh sách OrderDetail cho từng sản phẩm hoặc combo
        double total = 0;
        for (String productId : productIds) {
            Product product = iProductRepository.findById(productId)
                    .orElseThrow(() -> new EntityNotFoundException("Product with ID " + productId + " not found"));

            OrdersDetail orderDetail = new OrdersDetail();
            orderDetail.setOrdersDetailID(generateNextOrderDetailId());
            orderDetail.setOrders(savedOrder); // Liên kết OrderDetail với Order đã lưu
            orderDetail.setProduct(product); // Lưu Product vào OrderDetail
            iOrdersDetailRepository.save(orderDetail);

            total += product.getPrice(); // Cập nhật tổng giá
        }

        // 5. Tạo danh sách OrderDetail cho từng ProductCombo
        for (String productComboId : productComboIds) {
            ProductCombo productCombo = iProductComboRepository.findById(productComboId)
                    .orElseThrow(() -> new EntityNotFoundException("Product combo with ID " + productComboId + " not found"));

            OrdersDetail orderDetail = new OrdersDetail();
            orderDetail.setOrdersDetailID(generateNextOrderDetailId());
            orderDetail.setOrders(savedOrder); // Liên kết OrderDetail với Order đã lưu
            orderDetail.setProductCombo(productCombo); // Lưu ProductCombo vào OrderDetail
            iOrdersDetailRepository.save(orderDetail);

            total += productCombo.getPrice(); // Cập nhật tổng giá
        }

        // 6. Cập nhật tổng tiền cho Order
        savedOrder.setTotal(total);
        iOrdersRepository.save(savedOrder);

        return modelMapper.map(savedOrder, OrderResponse.class); // Trả về phản hồi đơn hàng
    }



    // Hàm sinh ID tiếp theo cho OrderDetail
    private String generateNextOrderDetailId() {
        OrdersDetail lastDetail = iOrdersDetailRepository.findTopByOrderByOrdersDetailIDDesc();
        if (lastDetail != null) {
            String lastId = lastDetail.getOrdersDetailID();
            int idNumber = Integer.parseInt(lastId.substring(2));
            return String.format("OD%03d", idNumber + 1);
        }
        return "OD001";
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
