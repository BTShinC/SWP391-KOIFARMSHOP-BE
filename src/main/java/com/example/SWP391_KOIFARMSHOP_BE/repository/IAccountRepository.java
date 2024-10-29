package com.example.SWP391_KOIFARMSHOP_BE.repository;

import com.example.SWP391_KOIFARMSHOP_BE.pojo.Account;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAccountRepository extends JpaRepository<Account, String> {
    Account findAccountByUserName(String userName);
    boolean existsByEmail(String email);
    Account findByEmail(String email);
    Account findByResetToken(String token);
    Account findTopByOrderByAccountIDDesc();
    Account findAccountByaccountID(String accountID);
    Account findRoleByaccountID(String role);
    boolean existsByuserName(String userName);
    boolean existsByaccountID(String accounID);
    Account findByUserName(String userName);
    List<Account> findByRole(Role role);

}