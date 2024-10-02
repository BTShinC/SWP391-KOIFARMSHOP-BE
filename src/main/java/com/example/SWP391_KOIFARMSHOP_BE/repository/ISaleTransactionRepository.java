package com.example.SWP391_KOIFARMSHOP_BE.repository;

import com.example.SWP391_KOIFARMSHOP_BE.pojo.SaleTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISaleTransactionRepository extends JpaRepository<SaleTransaction, Long> {
}
