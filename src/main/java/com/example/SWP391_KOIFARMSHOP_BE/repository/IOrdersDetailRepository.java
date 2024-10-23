package com.example.SWP391_KOIFARMSHOP_BE.repository;

import com.example.SWP391_KOIFARMSHOP_BE.pojo.Orders;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.OrdersDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IOrdersDetailRepository extends JpaRepository<OrdersDetail, String> {
    OrdersDetail findTopByOrderByOrdersDetailIDDesc(); // Để lấy OrdersDetail cuối cùng

    // Tìm danh sách OrdersDetail theo Orders
    List<OrdersDetail> findByOrders(Orders orders);
    List<OrdersDetail> findByType(String type);
}
