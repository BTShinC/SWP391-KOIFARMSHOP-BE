package com.example.SWP391_KOIFARMSHOP_BE.service;

import com.example.SWP391_KOIFARMSHOP_BE.exception.EntityNotFoundException;
import com.example.SWP391_KOIFARMSHOP_BE.model.AccountResponse;
import com.example.SWP391_KOIFARMSHOP_BE.model.ProductResponse;
import com.example.SWP391_KOIFARMSHOP_BE.model.RegisterRequest;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.Account;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.Product;
import com.example.SWP391_KOIFARMSHOP_BE.repository.IAccountRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountService{
    @Autowired
    private IAccountRepository iAccountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ModelMapper modelMapper;

    // Lấy tất cả Account
    public List<AccountResponse> getAllAccount() {
        List<Account> accounts = iAccountRepository.findAll();
        return accounts.stream()
                .map(account -> modelMapper.map(account, AccountResponse.class))
                .collect(Collectors.toList());
    }
    // Chỉnh sửa account
    public AccountResponse updateAccount(String accountID, RegisterRequest accountRequest) {
        Account account = iAccountRepository.findById(accountID)
                .orElseThrow(() -> new EntityNotFoundException("Account with ID " + accountID + " not found"));
        modelMapper.map(accountRequest, account);
        account.setRole(null);
        account.setOrders(null);
        Account updateAccount = iAccountRepository.save(account);
        return  modelMapper.map(updateAccount, AccountResponse.class);
    }

    // Xóa tài khoản
    public Account deleteAccount(String accountID) {
        Optional<Account> optionalAccount = iAccountRepository.findById(accountID);
        if (optionalAccount.isEmpty()) {
            throw new EntityNotFoundException("Account with ID " + accountID + " not found");
        }
        Account account = optionalAccount.get();
        account.setDeleted(true);
        return iAccountRepository.save(account);
    }


    // Lấy sản phẩm theo ID
    public AccountResponse getAccountByID(String accountID) {
        Account account = iAccountRepository.findById(accountID)
                .orElseThrow(() -> new EntityNotFoundException("Account with ID " + accountID + " not found"));
        return  modelMapper.map(account, AccountResponse.class);
    }


    public Account findByEmail(String email) {
        Account account = iAccountRepository.findByEmail(email);
        if (account == null) {
            throw new EntityNotFoundException("Account not found for email: " + email);
        }
        return account;
    }


    public void saveResetToken(String email, String token) {
        Account account = iAccountRepository.findByEmail(email);
        if (account != null) {
            account.setResetToken(token);
            iAccountRepository.save(account);
        }
    }

    public Account findByResetToken(String token) {
        return iAccountRepository.findByResetToken(token);
    }

    public String encode(String password) {
        return passwordEncoder.encode(password);
    }

    public void save(Account account) {
        iAccountRepository.save(account);
    }


}

