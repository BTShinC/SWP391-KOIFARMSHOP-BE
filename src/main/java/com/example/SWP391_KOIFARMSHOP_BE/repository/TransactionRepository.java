package com.example.SWP391_KOIFARMSHOP_BE.repository;

import com.example.SWP391_KOIFARMSHOP_BE.pojo.Account;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
     Transaction findTopByOrderByTransactionIDDesc();
     List<Transaction> findByAccountID(String accountID);
}
