package com.example.SWP391_KOIFARMSHOP_BE.repository;

import com.example.SWP391_KOIFARMSHOP_BE.pojo.Orders;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.OrdersDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrdersDetailRepository extends JpaRepository<OrdersDetail, String> {
    OrdersDetail findTopByOrderByOrdersDetailIDDesc();
}
