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
            String nextId = String.format("O%03d", idNumber + 1);
            return nextId;
        } else {
            return "T001";
        }
    }

    public TransactionReponse create(TransactionRequest transactionRequest){
        String accountid = transactionRequest.getAccountID();


        boolean checkexist = iAccountRepository.existsByaccountID(accountid);
        if(!checkexist){
            throw new EntityNotFoundException("Account not found with ID: " + accountid);
        }

        Transaction transaction = new Transaction();
        transaction.setTransactionID(generateTransactionID());
        transaction.setAccountID(accountid);
        transaction.setDate(transactionRequest.getDate());
        transaction.setStatus(transactionRequest.getStatus());
        transaction.setImage(transactionRequest.getImage());
        Transaction savetransaction =  transactionRepository.save(transaction);


        // Trả về FeedbackResponse
       TransactionReponse  response = new TransactionReponse();
        response.setTransactionID(savetransaction.getTransactionID());
        response.setAccountID(savetransaction.getAccountID());
        response.setStatus(savetransaction.getStatus());
        response.setDate(savetransaction.getDate());
        response.setImage(savetransaction.getImage());
        return response;
    }




}
