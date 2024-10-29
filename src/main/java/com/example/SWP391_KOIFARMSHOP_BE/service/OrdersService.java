package com.example.SWP391_KOIFARMSHOP_BE.service;

import com.example.SWP391_KOIFARMSHOP_BE.exception.EntityNotFoundException;
import com.example.SWP391_KOIFARMSHOP_BE.model.OrderRequest;
import com.example.SWP391_KOIFARMSHOP_BE.model.OrderResponse;
import com.example.SWP391_KOIFARMSHOP_BE.model.OrdersDetailResponse;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.*;
import com.example.SWP391_KOIFARMSHOP_BE.repository.*;
import jakarta.transaction.Transactional;
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
    private IPromotionRepository iPromotionRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Transactional
    public OrderResponse createOrderWithMultipleProducts(String accountId, List<String> productIds, List<String> productComboIds, String promotionID) {
        // 1. Kiểm tra Account
        Account account = iAccountRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException("Account with ID " + accountId + " not found"));

        // 2. Tạo Order mới
        Orders order = new Orders();
        String nextId = generateNextOrderId(); // Tạo ID mới
        order.setOrderID(nextId);
        order.setAccount(account);
        Date orderDate = new Date();  // Ngày hiện tại
        order.setDate(orderDate);
        order.setStatus("Đang xử lý");

        Promotion promotion = null;
        double discountValue = 0;

        // Nếu có mã khuyến mãi, kiểm tra Promotion
        if (promotionID != null) {
            promotion = iPromotionRepository.findById(promotionID)
                    .orElseThrow(() -> new EntityNotFoundException("Promotion with ID " + promotionID + " not found"));

            // Kiểm tra ngày Order có nằm trong khoảng thời gian của Promotion không
            if (orderDate.compareTo(promotion.getStartDate()) >= 0 && orderDate.compareTo(promotion.getEndDate()) <= 0) {
                discountValue = promotion.getDiscountValue();  // Lấy giá trị giảm giá
                order.setPromotion(promotion);  // Gán Promotion cho Order
            }
        }

        // 3. Lưu Order trước khi tạo OrderDetail
        Orders savedOrder = iOrdersRepository.save(order);

        // 4. Khởi tạo các biến để tính tổng tiền
        double total = 0;  // Tổng tiền chưa giảm giá
        double discountedTotal = 0;  // Tổng tiền sau khi giảm giá

        // Xử lý Product
        for (String productId : productIds) {
            Product product = iProductRepository.findById(productId)
                    .orElseThrow(() -> new EntityNotFoundException("Product with ID " + productId + " not found"));

            // Kiểm tra trạng thái của Product
            if (!"Còn hàng".equals(product.getStatus())) {
                throw new IllegalArgumentException("Product with ID " + productId + " is not available for ordering.");
            }

            // Tạo OrderDetail cho sản phẩm
            OrdersDetail orderDetail = new OrdersDetail();
            orderDetail.setOrdersDetailID(generateNextOrderDetailId());
            orderDetail.setOrders(savedOrder);
            orderDetail.setProduct(product);
            orderDetail.setType(product.getType());
            orderDetail.setDate(orderDate);

            double originalPrice = product.getPrice();  // Giá gốc
            double discountedPrice = originalPrice;

            // Áp dụng khuyến mãi cho Product nếu có
            if (promotion != null) {
                discountedPrice = originalPrice - (originalPrice * discountValue / 100);
            }

            // Gán giá gốc và giá giảm vào OrdersDetail
            orderDetail.setPrice(originalPrice);
            orderDetail.setDiscountedPrice(discountedPrice);

            total += originalPrice;  // Cộng vào tổng tiền gốc
            discountedTotal += discountedPrice;  // Cộng vào tổng tiền giảm giá

            // Lưu OrdersDetail
            iOrdersDetailRepository.save(orderDetail);

            // Sau khi lưu OrdersDetail, cập nhật trạng thái của Product
            product.setStatus("Đã bán");
            iProductRepository.save(product);
        }

        // Xử lý ProductCombo
        for (String productComboId : productComboIds) {
            ProductCombo productCombo = iProductComboRepository.findById(productComboId)
                    .orElseThrow(() -> new EntityNotFoundException("Product combo with ID " + productComboId + " not found"));

            // Kiểm tra trạng thái của ProductCombo
            if (!"Còn hàng".equals(productCombo.getStatus())) {
                throw new IllegalArgumentException("ProductCombo with ID " + productComboId + " is not available for ordering.");
            }

            // Tạo OrderDetail cho ProductCombo
            OrdersDetail orderDetail = new OrdersDetail();
            orderDetail.setOrdersDetailID(generateNextOrderDetailId());
            orderDetail.setOrders(savedOrder);
            orderDetail.setProductCombo(productCombo);
            orderDetail.setType(productCombo.getType());
            orderDetail.setDate(orderDate);

            double originalComboPrice = productCombo.getPrice();  // Giá gốc combo
            double discountedComboPrice = originalComboPrice;

            // Áp dụng khuyến mãi cho ProductCombo nếu có
            if (promotion != null) {
                discountedComboPrice = originalComboPrice - (originalComboPrice * discountValue / 100);
            }

            // Gán giá gốc và giá giảm vào OrdersDetail
            orderDetail.setPrice(originalComboPrice);
            orderDetail.setDiscountedPrice(discountedComboPrice);

            total += originalComboPrice;  // Cộng vào tổng tiền gốc
            discountedTotal += discountedComboPrice;  // Cộng vào tổng tiền giảm giá

            // Lưu OrdersDetail
            iOrdersDetailRepository.save(orderDetail);

            // Cập nhật trạng thái của ProductCombo
            productCombo.setStatus("Đã bán");
            iProductComboRepository.save(productCombo);
        }

        // 6. Cập nhật tổng tiền cho Order
        savedOrder.setTotal(total);  // Tổng tiền chưa giảm giá
        savedOrder.setDiscountedTotal(discountedTotal);  // Tổng tiền đã giảm giá
        iOrdersRepository.save(savedOrder);

        // Trả về phản hồi đơn hàng
        return modelMapper.map(savedOrder, OrderResponse.class);
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

    // Lấy ra tất cả các order
    public List<OrderResponse> getAllOrders() {
        return iOrdersRepository.findAll().stream()
                .map(order -> modelMapper.map(order, OrderResponse.class))
                .collect(Collectors.toList());
    }
    // Lấy order theo account
    public List<OrderResponse> getOrdersByAccountId(String accountId) {
        Account account = iAccountRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException("Account with ID " + accountId + " not found"));

        List<Orders> orders = iOrdersRepository.findAllByAccount_AccountID(accountId);
        return orders.stream()
                .map(order -> modelMapper.map(order, OrderResponse.class))
                .collect(Collectors.toList());
    }

    // Lấy order theo ID
    @Transactional
    public OrderResponse getOrderById(String orderId) {
        Orders order = iOrdersRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Orders with ID " + orderId + " not found"));

        // Thủ công truy xuất danh sách OrdersDetail từ order (nếu bạn sử dụng FetchType.LAZY)
        order.getOrdersDetail().size(); // Kích hoạt việc tải dữ liệu OrdersDetail

        OrderResponse orderResponse = modelMapper.map(order, OrderResponse.class);

        // Ánh xạ thủ công danh sách OrdersDetail
        List<OrdersDetailResponse> ordersDetailResponses = order.getOrdersDetail().stream()
                .map(ordersDetail -> {
                    OrdersDetailResponse detailResponse = new OrdersDetailResponse();
                    detailResponse.setOrdersDetailID(ordersDetail.getOrdersDetailID());

                    if (ordersDetail.getProduct() != null) {
                        detailResponse.setProductID(ordersDetail.getProduct().getProductID());
                        detailResponse.setProductName(ordersDetail.getProduct().getProductName());
                        detailResponse.setProductPrice(ordersDetail.getProduct().getPrice());
                    }

                    if (ordersDetail.getProductCombo() != null) {
                        detailResponse.setProductComboID(ordersDetail.getProductCombo().getProductComboID());
                        detailResponse.setComboName(ordersDetail.getProductCombo().getComboName());
                        detailResponse.setComboPrice(ordersDetail.getProductCombo().getPrice());
                    }

                    return detailResponse;
                })
                .collect(Collectors.toList());

        orderResponse.setOrdersDetails(ordersDetailResponses);
        return orderResponse;
    }



    // Update Order
    @Transactional  // Đảm bảo tất cả các thay đổi được thực hiện trong cùng một giao dịch
    public OrderResponse updateOrder(String orderId, OrderRequest orderRequest) {
        // Lấy đơn hàng hiện tại từ cơ sở dữ liệu
        Orders existingOrder = iOrdersRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Orders with ID " + orderId + " not found"));

        // Cập nhật thông tin đơn hàng từ request
        existingOrder.setStatus(orderRequest.getStatus());
//        existingOrder.setDate(orderRequest.getDate());
//        existingOrder.setDescription(orderRequest.getDescription());

        // Lưu lại đơn hàng sau khi cập nhật trạng thái
        Orders updatedOrder = iOrdersRepository.save(existingOrder);

        // Trả về phản hồi sau khi cập nhật thành công
        return modelMapper.map(updatedOrder, OrderResponse.class);
    }




    // Delete Order
    public void deleteOrder(String orderId) {
        Orders order = iOrdersRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Orders with ID " + orderId + " not found"));
        iOrdersRepository.delete(order);
    }



}
