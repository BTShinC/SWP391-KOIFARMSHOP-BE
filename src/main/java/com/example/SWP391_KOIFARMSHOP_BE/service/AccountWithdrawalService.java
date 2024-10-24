package com.example.SWP391_KOIFARMSHOP_BE.service;

import com.example.SWP391_KOIFARMSHOP_BE.model.AccountWithdrawalReponse;
import com.example.SWP391_KOIFARMSHOP_BE.model.AccountWithdrawalRequest;
import com.example.SWP391_KOIFARMSHOP_BE.model.ProductResponse;
import com.example.SWP391_KOIFARMSHOP_BE.model.TransactionReponse;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.Account;
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

   @Autowired
   AccountService accountService;

    private String generateAccountWithdrawalId() {
        AccountWithdrawal accountWithdrawal = iAccountWithdrawal.findTopByOrderByAccountWithdrawalIdDesc();
        if (accountWithdrawal != null) {
            String lastId = accountWithdrawal.getAccountWithdrawalId();
            try {
                int idNumber = Integer.parseInt(lastId.substring(2)); // Bỏ qua "AW"
                String nextId = String.format("AW%03d", idNumber + 1);
                return nextId;
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid format for AccountWithdrawalId: " + lastId);
            }
        } else {
            return "AW001";
        }
    }

   public AccountWithdrawal create (AccountWithdrawalRequest accountWithdrawalRequest){

       String accountID = accountWithdrawalRequest.getAccountID();

       // Kiểm tra sự tồn tại của account
       boolean checkexist = iAccountRepository.existsByaccountID(accountID);
       if (!checkexist) {
           throw new EntityNotFoundException("Account not found with ID: " + accountID);
       }

       // Tạo đối tượng Transaction mới
       AccountWithdrawal accountWithdrawal = new AccountWithdrawal();

       accountWithdrawal.setAccountWithdrawalId(generateAccountWithdrawalId());
       accountWithdrawal.setAccountID(accountWithdrawalRequest.getAccountID());
       accountWithdrawal.setDate(accountWithdrawalRequest.getDate());
       accountWithdrawal.setPricesend(accountWithdrawalRequest.getPricesend());
       accountWithdrawal.setStatus("Chờ xử lí");
       accountWithdrawal.setAccount_number(accountWithdrawalRequest.getAccount_number());
       accountWithdrawal.setAccount_holder_name(accountWithdrawalRequest.getAccount_holder_name());
       accountWithdrawal.setBank_branch(accountWithdrawalRequest.getBank_branch());
       accountWithdrawal.setBank_name(accountWithdrawalRequest.getBank_name());
       System.out.println(accountWithdrawal);
       // Lưu đối tượng transaction vào cơ sở dữ liệu
       AccountWithdrawal savedaccountWithdrawal = iAccountWithdrawal.save(accountWithdrawal);
       System.out.println(savedaccountWithdrawal);
       // Trả về đối tượng Transaction đã được lưu
       return savedaccountWithdrawal;
   }

    public List<AccountWithdrawalReponse> getAllAccountWithdrawal() {
        List<AccountWithdrawal> transactions = iAccountWithdrawal.findAll();
        return transactions.stream()
                .map(transaction -> modelMapper.map(transaction, AccountWithdrawalReponse.class))
                .collect(Collectors.toList());
    }

    public List<AccountWithdrawalReponse> getAccountWithdrawalByAccountID(String accountID) {
        List<AccountWithdrawal> transactions = iAccountWithdrawal.findAccountWithdrawalByAccountID(accountID);
        if (transactions == null) {
            throw new IllegalArgumentException("Product with name '" + accountID + "' already exists.");
        }
        return transactions.stream()
                .map(transaction -> modelMapper.map(transaction, AccountWithdrawalReponse.class))
                .collect(Collectors.toList());
    }

    public AccountWithdrawalReponse updateAccountWithdrawal(String accountWithdrawalId) {

        AccountWithdrawal accountWithdrawal = iAccountWithdrawal.findById(accountWithdrawalId)
                .orElseThrow(() -> new EntityNotFoundException("AccountWithdrawal not found with ID: " + accountWithdrawalId));

        if (!"Chờ xử lí".equals(accountWithdrawal.getStatus())) {
            throw new IllegalArgumentException("Only withdrawals with status 'Chờ xử lí' can be processed.");
        }
        String accountID = accountWithdrawal.getAccountID();
        accountService.deductAccountBalance(accountID,accountWithdrawal.getPricesend());
        accountWithdrawal.setStatus("Đã hoàn tiền");
        AccountWithdrawal updatedAccountWithdrawal = iAccountWithdrawal.save(accountWithdrawal);

        return modelMapper.map(updatedAccountWithdrawal, AccountWithdrawalReponse.class);
    }

}
