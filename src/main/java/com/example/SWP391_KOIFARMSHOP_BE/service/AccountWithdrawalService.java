package com.example.SWP391_KOIFARMSHOP_BE.service;

import com.example.SWP391_KOIFARMSHOP_BE.model.AccountWithdrawalReponse;
import com.example.SWP391_KOIFARMSHOP_BE.model.AccountWithdrawalRequest;
import com.example.SWP391_KOIFARMSHOP_BE.model.TransactionReponse;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.AccountWithdrawal;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.Transaction;
import com.example.SWP391_KOIFARMSHOP_BE.repository.IAccountRepository;
import com.example.SWP391_KOIFARMSHOP_BE.repository.IAccountWithdrawal;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountWithdrawalService {

   @Autowired
    IAccountWithdrawal iAccountWithdrawal;
   @Autowired
    IAccountRepository iAccountRepository;

   @Autowired
   ModelMapper modelMapper;

    private  String generateAccountWithdrawalId() {
        AccountWithdrawal  accountWithdrawal = iAccountWithdrawal.findTopByOrderByAccountWithdrawalIdDesc();
        if (accountWithdrawal != null) {
            String lastId = accountWithdrawal.getAccountWithdrawalId();
            int idNumber = Integer.parseInt(lastId.substring(1));
            String nextId = String.format("AW%03d", idNumber + 1);
            return nextId;
        } else {
            return "AW001";
        }
    }

   public AccountWithdrawal create (AccountWithdrawalRequest accountWithdrawalRequest){

       String accountid = accountWithdrawalRequest.getAccountId();

       // Kiểm tra sự tồn tại của account
       boolean checkexist = iAccountRepository.existsById(accountid);
       if (!checkexist) {
           throw new EntityNotFoundException("Account not found with ID: " + accountid);
       }

       // Tạo đối tượng Transaction mới
       AccountWithdrawal accountWithdrawal = new AccountWithdrawal();
       accountWithdrawal.setAccountWithdrawalId(generateAccountWithdrawalId());
       accountWithdrawal.setAccountId(accountWithdrawalRequest.getAccountId());
       accountWithdrawal.setDate(accountWithdrawalRequest.getDate());
       accountWithdrawal.setPricesend(accountWithdrawalRequest.getPricesend());
       accountWithdrawal.setStatus("Chờ xử lí");

       // Lưu đối tượng transaction vào cơ sở dữ liệu
       AccountWithdrawal savedaccountWithdrawal = iAccountWithdrawal.save(accountWithdrawal);

       // Trả về đối tượng Transaction đã được lưu
       return savedaccountWithdrawal;
   }

    public List<AccountWithdrawalReponse> getAllAccountWithdrawal() {
        List<AccountWithdrawal> transactions = iAccountWithdrawal.findAll();
        return transactions.stream()
                .map(transaction -> modelMapper.map(transaction, AccountWithdrawalReponse.class))
                .collect(Collectors.toList());
    }
}
