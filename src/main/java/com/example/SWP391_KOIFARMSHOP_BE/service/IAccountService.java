package com.example.SWP391_KOIFARMSHOP_BE.service;

import com.example.SWP391_KOIFARMSHOP_BE.pojo.Account;

import java.util.List;
import java.util.Optional;

public interface IAccountService {
    public List<Account> getAllAccount();
    public Account insertAccount(Account account);
    public Account updateAccount(long accountID, Account account);
    public Account deleteAccount (long accountID);
    public Optional<Account> getAccountByID(long accountID);
}
