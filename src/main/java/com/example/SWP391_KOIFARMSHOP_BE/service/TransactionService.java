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


import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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

    public Transaction create(TransactionRequest transactionRequest) {
        String accountid = transactionRequest.getAccountID();

        // Kiểm tra sự tồn tại của account
        boolean checkexist = iAccountRepository.existsById(accountid);
        if (!checkexist) {
            throw new EntityNotFoundException("Account not found with ID: " + accountid);
        }

        // Tạo đối tượng Transaction mới
        Transaction transaction = new Transaction();
        transaction.setTransactionID(generateTransactionID());
        transaction.setAccountID(accountid);
        transaction.setDate(transactionRequest.getDate());
        transaction.setPrice(transactionRequest.getPrice());

        // Lưu đối tượng transaction vào cơ sở dữ liệu
        Transaction savedTransaction = transactionRepository.save(transaction);

        // Trả về đối tượng Transaction đã được lưu
        return savedTransaction;
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
    public String creatURL (TransactionRequest transactionRequest) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime createDate = LocalDateTime.now();
        String formattedCreateDate = createDate.format(formatter);

        //code
        Transaction transaction =create(transactionRequest);
        float money = transaction.getPrice() *100;
        String amount = String.valueOf((int) money);


        //

        String tmnCode = "7XL12PHS";
        String secretKey = "LBFKRMUSBR85Y6JRKJPKF15M71XSEW8T";
        String vnpUrl = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
        String returnUrl = "http://103.90.227.69/vnpay/response?transactionID=" + transaction.getTransactionID();
        String currCode = "VND";
        Map<String, String> vnpParams = new TreeMap<>();
        vnpParams.put("vnp_Version", "2.1.0");
        vnpParams.put("vnp_Command", "pay");
        vnpParams.put("vnp_TmnCode", tmnCode);
        vnpParams.put("vnp_Locale", "vn");
        vnpParams.put("vnp_CurrCode", currCode);
        vnpParams.put("vnp_TxnRef", transaction.getTransactionID());
        vnpParams.put("vnp_OrderInfo", "Thanh toan cho ma GD: " + transaction.getTransactionID());
        vnpParams.put("vnp_OrderType", "other");
        vnpParams.put("vnp_Amount", amount);
        vnpParams.put("vnp_ReturnUrl", returnUrl);
        vnpParams.put("vnp_CreateDate", formattedCreateDate);
        vnpParams.put("vnp_IpAddr", "103.90.227.69");

        StringBuilder signDataBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : vnpParams.entrySet()) {

            signDataBuilder.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8));
            signDataBuilder.append("=");
            signDataBuilder.append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8));
            signDataBuilder.append("&");
        }
        signDataBuilder.deleteCharAt( signDataBuilder.length() - 1); // Remove last '&'
        String signData = signDataBuilder.toString();
        String signed = generateHMAC (secretKey, signData);
        vnpParams.put("vnp_SecureHash", signed);

        StringBuilder urlBuilder = new StringBuilder(vnpUrl);
        urlBuilder.append("?");
        for (Map.Entry<String, String> entry : vnpParams.entrySet()) {

            urlBuilder.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8));
            urlBuilder.append("=");
            urlBuilder.append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8));

            urlBuilder.append("&");
        }
        urlBuilder.deleteCharAt( urlBuilder.length() - 1); // Remove last '&'
        return urlBuilder.toString();
    }
    private String generateHMAC (String secretKey, String signData) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac hmacSha512 = Mac.getInstance(  "HmacSHA512");
        SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8),  "HmacSHA512");
        hmacSha512.init(keySpec);
        byte[] hmacBytes = hmacSha512.doFinal(signData.getBytes(StandardCharsets.UTF_8));

        StringBuilder result = new StringBuilder();
        for (byte b: hmacBytes) {
            result.append(String.format("%02x",b));
        }
        return result.toString();
    }



}
