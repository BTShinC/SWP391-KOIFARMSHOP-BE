package com.example.SWP391_KOIFARMSHOP_BE.repository;

import com.example.SWP391_KOIFARMSHOP_BE.pojo.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAccountRepository extends JpaRepository<Account, Long> {
    Account findAccountByUserName(String userName);
    boolean existsByEmail(String email);
    Account findByEmail(String email);
    Account findByResetToken(String token);
   // boolean existsByPhone(String phoneNumber);
   Account findAccountByaccountID(long accountID);
}