package com.example.SWP391_KOIFARMSHOP_BE.repository;

import com.example.SWP391_KOIFARMSHOP_BE.pojo.Orders;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IOrdersRepository extends JpaRepository<Orders, String> {
    Orders findTopByOrderByOrderIDDesc();
    Orders findByorderID(String orderID);
    List<Orders> findAllByAccount_AccountID(String accountId);
}
