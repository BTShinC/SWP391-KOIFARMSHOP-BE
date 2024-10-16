package com.example.SWP391_KOIFARMSHOP_BE.service;

import com.example.SWP391_KOIFARMSHOP_BE.model.FeedbackResponse;
import com.example.SWP391_KOIFARMSHOP_BE.model.TransactionReponse;
import com.example.SWP391_KOIFARMSHOP_BE.model.TransactionRequest;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.Account;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.Feedback;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.Transaction;
import com.example.SWP391_KOIFARMSHOP_BE.repository.IAccountRepository;
import com.example.SWP391_KOIFARMSHOP_BE.repository.TransactionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Date;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {
     @Autowired
    IAccountRepository  iAccountRepository;

     @Autowired
     TransactionRepository transactionRepository;

    @Autowired
    private ModelMapper modelMapper;

    private  String generateTransactionID() {
        Transaction lasttransaction = transactionRepository.findTopByOrderByTransactionIDDesc();
        if (lasttransaction != null) {
            String lastId = lasttransaction.getTransactionID();
            int idNumber = Integer.parseInt(lastId.substring(1));
            String nextId = String.format("T%03d", idNumber + 1);
            return nextId;
        } else {
            return "T001";
        }
    }

    public TransactionReponse create(TransactionRequest transactionRequest){
        String accountid = transactionRequest.getAccountID();


        boolean checkexist = iAccountRepository.existsById(accountid);
        if(!checkexist){
            throw new EntityNotFoundException("Account not found with ID: " + accountid);
        }

        Transaction transaction = new Transaction();
        transaction.setTransactionID(generateTransactionID());
        transaction.setAccountID(accountid);
        transaction.setDate(transactionRequest.getDate());
        transaction.setStatus(transactionRequest.getStatus());
        transaction.setImage(transactionRequest.getImage());
        transaction.setPrice(transactionRequest.getPrice());
        Transaction savetransaction =  transactionRepository.save(transaction);


        // Trả về FeedbackResponse
       TransactionReponse  response = new TransactionReponse();
        response.setTransactionID(savetransaction.getTransactionID());
        response.setAccountID(savetransaction.getAccountID());
        response.setStatus(savetransaction.getStatus());
        response.setDate(savetransaction.getDate());
        response.setImage(savetransaction.getImage());
        response.setPrice(savetransaction.getPrice());
        return response;
    }
    public List<TransactionReponse> getAllTransactions() {
        List<Transaction> transactions = transactionRepository.findAll();
        return transactions.stream()
                .map(transaction -> modelMapper.map(transaction, TransactionReponse.class))
                .collect(Collectors.toList());
    }
    public List<TransactionReponse> getTransactionsByAccountId(String accountId) {
        List<Transaction> transactions = transactionRepository.findByAccountID(accountId);
        return transactions.stream()
                .map(transaction -> modelMapper.map(transaction, TransactionReponse.class))
                .collect(Collectors.toList());
    }
    public TransactionReponse updateTransactionStatus(String transactionId, String newStatus) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new EntityNotFoundException("Transaction not found with ID: " + transactionId));

        transaction.setStatus(newStatus);
        Transaction updatedTransaction = transactionRepository.save(transaction);

        return modelMapper.map(updatedTransaction, TransactionReponse.class);
    }

    public List<TransactionReponse> searchTransactionsByStatus(String status) {
        List<Transaction> transactions = transactionRepository.findAll();
        return transactions.stream()
                .filter(transaction -> transaction.getStatus().toLowerCase().contains(status.toLowerCase()))
                .map(transaction -> modelMapper.map(transaction, TransactionReponse.class))
                .collect(Collectors.toList());
    }



}
