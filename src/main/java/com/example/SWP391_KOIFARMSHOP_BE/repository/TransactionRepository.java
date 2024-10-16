package com.example.SWP391_KOIFARMSHOP_BE.repository;

import com.example.SWP391_KOIFARMSHOP_BE.pojo.Account;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
     Transaction findTopByOrderByTransactionIDDesc();
     List<Transaction> findByAccountID(String accountID);
     @Query("SELECT t FROM Transaction t WHERE LOWER(t.status) LIKE LOWER(CONCAT('%', :status, '%'))")
     List<Transaction> searchByStatus(@Param("status") String status);
}
