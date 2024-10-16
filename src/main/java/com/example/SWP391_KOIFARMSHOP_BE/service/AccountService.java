package com.example.SWP391_KOIFARMSHOP_BE.service;

import com.example.SWP391_KOIFARMSHOP_BE.exception.EntityNotFoundException;
import com.example.SWP391_KOIFARMSHOP_BE.model.AccountRequest;
import com.example.SWP391_KOIFARMSHOP_BE.model.AccountResponse;
import com.example.SWP391_KOIFARMSHOP_BE.model.ProductResponse;
import com.example.SWP391_KOIFARMSHOP_BE.model.RegisterRequest;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.Account;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.Product;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.Role;
import com.example.SWP391_KOIFARMSHOP_BE.repository.IAccountRepository;
import com.example.SWP391_KOIFARMSHOP_BE.repository.IRoleRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class AccountService{
    @Autowired
    private IAccountRepository iAccountRepository;
    @Autowired
    private IRoleRepository iRoleRepository;
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
    public AccountResponse updateAccount(String accountID, AccountRequest accountUpdateRequest) {
        Account account = iAccountRepository.findById(accountID)
                .orElseThrow(() -> new EntityNotFoundException("Account with ID " + accountID + " not found"));

        // Cập nhật các trường thông tin
        account.setFullName(accountUpdateRequest.getFullName());
        account.setAddress(accountUpdateRequest.getAddress());
        account.setEmail(accountUpdateRequest.getEmail());
        account.setPhoneNumber(accountUpdateRequest.getPhoneNumber());
        account.setAccountBalance(accountUpdateRequest.getAccountBalance());
        account.setImage(accountUpdateRequest.getImage());

        // Xử lý role
        Role role = iRoleRepository.findById(accountUpdateRequest.getRoleID())
                .orElseThrow(() -> new EntityNotFoundException("Role not found with ID: " + accountUpdateRequest.getRoleID()));
        account.setRole(role);

        // Lưu tài khoản đã cập nhật
        Account updatedAccount = iAccountRepository.save(account);

        // Trả về AccountResponse với roleName
        AccountResponse response = modelMapper.map(updatedAccount, AccountResponse.class);
        response.setRoleName(role.getRoleName());
        return response;
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


    public AccountResponse updateAccountBalance(String accountID, double amount) {
        Account account = iAccountRepository.findById(accountID)
                .orElseThrow(() -> new EntityNotFoundException("Account with ID " + accountID + " not found"));

        // Cộng thêm số tiền mới vào số dư hiện tại
        double updatedBalance = account.getAccountBalance() + amount;
        account.setAccountBalance(updatedBalance);

        Account updatedAccount = iAccountRepository.save(account);

        return modelMapper.map(updatedAccount, AccountResponse.class);
    }

    public AccountResponse deductAccountBalance(String accountID, double amount) {
        Account account = iAccountRepository.findById(accountID)
                .orElseThrow(() -> new EntityNotFoundException("Account with ID " + accountID + " not found"));

        // Kiểm tra số dư có đủ để trừ không
        if (account.getAccountBalance() < amount) {
            throw new IllegalArgumentException("Insufficient account balance to perform this transaction.");
        }

        // Trừ số tiền từ số dư hiện tại
        double updatedBalance = account.getAccountBalance() - amount;
        account.setAccountBalance(updatedBalance);

        Account updatedAccount = iAccountRepository.save(account);

        return modelMapper.map(updatedAccount, AccountResponse.class);
    }

}

