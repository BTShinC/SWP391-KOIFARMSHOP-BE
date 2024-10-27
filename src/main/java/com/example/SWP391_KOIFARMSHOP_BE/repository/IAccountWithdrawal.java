package com.example.SWP391_KOIFARMSHOP_BE.repository;

import com.example.SWP391_KOIFARMSHOP_BE.pojo.Account;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.AccountWithdrawal;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.CarePackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAccountWithdrawal extends JpaRepository<AccountWithdrawal, String> {
    AccountWithdrawal findTopByOrderByAccountWithdrawalIdDesc();
    boolean existsByAccountID(String accountID);
    List<AccountWithdrawal> findAccountWithdrawalByAccountID(String accountID);
}
