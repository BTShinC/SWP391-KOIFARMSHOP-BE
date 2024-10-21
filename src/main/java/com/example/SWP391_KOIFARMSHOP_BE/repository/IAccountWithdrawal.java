package com.example.SWP391_KOIFARMSHOP_BE.repository;

import com.example.SWP391_KOIFARMSHOP_BE.pojo.AccountWithdrawal;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.CarePackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAccountWithdrawal extends JpaRepository<AccountWithdrawal, String> {


}
