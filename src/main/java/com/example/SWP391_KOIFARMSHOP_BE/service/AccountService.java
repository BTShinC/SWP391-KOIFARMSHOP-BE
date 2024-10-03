package com.example.SWP391_KOIFARMSHOP_BE.service;

import com.example.SWP391_KOIFARMSHOP_BE.exception.EntityNotFoundException;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.Account;
import com.example.SWP391_KOIFARMSHOP_BE.repository.IAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class AccountService implements IAccountService {
    @Autowired
    private IAccountRepository iAccountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<Account> getAllAccount() {
        return iAccountRepository.findAll();
    }

    @Override
    public Account insertAccount(Account account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        return iAccountRepository.save(account);
    }

    @Override
    public Account updateAccount(long accountID, Account account) {
        Optional<Account> optionalAccount = iAccountRepository.findById(accountID);
        if (optionalAccount.isEmpty()) {
            throw new EntityNotFoundException("Account not found");
        }

        Account o = optionalAccount.get();
        o.setUserName(account.getUsername());
        o.setPassword(passwordEncoder.encode(account.getPassword()));
        o.setFullName(account.getFullName());
        o.setAddress(account.getAddress());
        o.setEmail(account.getEmail());
        o.setPhoneNumber(account.getPhoneNumber());
        o.setAccountBalance(account.getAccountBalance());
        o.setImage(account.getImage());

        return iAccountRepository.save(o);
    }

    @Override
    public Account deleteAccount(long accountID) {
        Optional<Account> optionalAccount = iAccountRepository.findById(accountID);
        if (optionalAccount.isEmpty()) {
            throw new EntityNotFoundException("Account not found");
        }
        Account account = optionalAccount.get();
        account.setDeleted(true);
        return iAccountRepository.save(account);
    }


    @Override
    public Optional<Account> getAccountByID(long accountID) {
        Optional<Account> account = iAccountRepository.findById(accountID);
        if (account.isEmpty()) {
            throw new EntityNotFoundException("Account not found");
        }
        return account;
    }

    @Override
    public Account findByEmail(String email) {
        Account account = iAccountRepository.findByEmail(email);
        if (account == null) {
            throw new EntityNotFoundException("Account not found for email: " + email);
        }
        return account;
    }

    @Override
    public void saveResetToken(String email, String token) {
        Account account = iAccountRepository.findByEmail(email);
        if (account != null) {
            account.setResetToken(token);
            iAccountRepository.save(account);
        }
    }
    @Override
    public Account findByResetToken(String token) {
        return iAccountRepository.findByResetToken(token);
    }
    @Override
    public String encode(String password) {
        return passwordEncoder.encode(password);
    }
    @Override
    public void save(Account account) {
        iAccountRepository.save(account);
    }


}

