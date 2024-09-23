package com.example.SWP391_KOIFARMSHOP_BE.service;

import com.example.SWP391_KOIFARMSHOP_BE.pojo.Account;
import com.example.SWP391_KOIFARMSHOP_BE.repository.IAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class AccountService implements IAccountService {
    @Autowired
    private IAccountRepository iAccountRepository;

    @Override
    public List<Account> getAllAccount() {
        return iAccountRepository.findAll();
    }

    @Override
    public Account insertAccount(Account account) {
        return iAccountRepository.save(account);
    }

    @Override
    public Account updateAccount(long accountID, Account account) {
        Account o = iAccountRepository.getById(accountID);
        if(o != null){
            o.setUserName(account.getUserName());
            o.setPassword(account.getPassword());
            o.setFullName(account.getFullName());
            o.setAddress(account.getAddress());
            o.setEmail(account.getEmail());
            o.setPhoneNumber(account.getPhoneNumber());
            o.setAccountBalance(account.getAccountBalance());
            o.setImage(account.getImage());
            return iAccountRepository.save(o);
        }
        return null;
    }

    @Override
    public void deleteAccount(long accountID) {
        iAccountRepository.deleteById(accountID);
    }

    @Override
    public Optional<Account> getAccountByID(long accountID) {
        return iAccountRepository.findById(accountID);
    }
}

