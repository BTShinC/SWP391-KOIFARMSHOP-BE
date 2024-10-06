package com.example.SWP391_KOIFARMSHOP_BE.repository;

import com.example.SWP391_KOIFARMSHOP_BE.pojo.Payment;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPaymentRepository extends JpaRepository<Payment, String> {
    Payment findTopByOrderByPaymentIDDesc();
}
