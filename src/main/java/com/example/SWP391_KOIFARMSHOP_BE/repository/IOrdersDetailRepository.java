package com.example.SWP391_KOIFARMSHOP_BE.repository;

import com.example.SWP391_KOIFARMSHOP_BE.pojo.Orders;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.OrdersDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface IOrdersDetailRepository extends JpaRepository<OrdersDetail, String> {
    OrdersDetail findTopByOrderByOrdersDetailIDDesc(); // Để lấy OrdersDetail cuối cùng

    // Tìm danh sách OrdersDetail theo Orders
    List<OrdersDetail> findByOrders(Orders orders);
    List<OrdersDetail> findByType(String type);

    // Lấy tất cả OrdersDetail có trạng thái "Đã hoàn thành" và thuộc loại đã cho trong khoảng ngày
    @Query("SELECT od FROM OrdersDetail od WHERE od.orders.status = 'Đã hoàn thành' AND od.type = :type AND od.date BETWEEN :startDate AND :endDate")
    List<OrdersDetail> findCompletedOrdersByTypeAndDate(String type, Date startDate, Date endDate);
}
