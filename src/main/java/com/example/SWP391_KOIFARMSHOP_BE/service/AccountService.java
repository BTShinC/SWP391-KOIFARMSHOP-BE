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


    public List<AccountResponse> getAllAccount() {
        List<Account> accounts = iAccountRepository.findAll();
        return accounts.stream()
                .map(account -> modelMapper.map(account, AccountResponse.class))
                .collect(Collectors.toList());
    }

    public AccountResponse updateAccount(String accountID, AccountRequest accountUpdateRequest) {
        Account account = iAccountRepository.findById(accountID)
                .orElseThrow(() -> new EntityNotFoundException("Account with ID " + accountID + " not found"));


        account.setFullName(accountUpdateRequest.getFullName());
        account.setAddress(accountUpdateRequest.getAddress());
        account.setEmail(accountUpdateRequest.getEmail());
        account.setPhoneNumber(accountUpdateRequest.getPhoneNumber());
        account.setAccountBalance(accountUpdateRequest.getAccountBalance());
        account.setImage(accountUpdateRequest.getImage());

        Role role = iRoleRepository.findByRoleName(accountUpdateRequest.getRoleName())
                .orElseThrow(() -> new EntityNotFoundException("Role not found with ID: " + accountUpdateRequest.getRoleName()));
        account.setRole(role);


        Account updatedAccount = iAccountRepository.save(account);


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


        double updatedBalance = account.getAccountBalance() + amount;
        account.setAccountBalance(updatedBalance);

        Account updatedAccount = iAccountRepository.save(account);

        return modelMapper.map(updatedAccount, AccountResponse.class);
    }

    public String updateAccountBalancRefund(String accountID, double amount) {
        Account account = iAccountRepository.findById(accountID)
                .orElseThrow(() -> new EntityNotFoundException("Account with ID " + accountID + " not found"));


        double updatedBalance = account.getAccountBalance() + amount;
        account.setAccountBalance(updatedBalance);

        iAccountRepository.save(account);

        return "Account balance updated successfully. New balance: " + updatedBalance;
    }

    public AccountResponse deductAccountBalance(String accountID, double amount) {
        Account account = iAccountRepository.findById(accountID)
                .orElseThrow(() -> new EntityNotFoundException("Account with ID " + accountID + " not found"));


        if (account.getAccountBalance() < amount) {
            throw new IllegalArgumentException("Insufficient account balance to perform this transaction.");
        }


        double updatedBalance = account.getAccountBalance() - amount;
        account.setAccountBalance(updatedBalance);

        Account updatedAccount = iAccountRepository.save(account);

        return modelMapper.map(updatedAccount, AccountResponse.class);
    }

}

