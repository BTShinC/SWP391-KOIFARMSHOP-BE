package com.example.SWP391_KOIFARMSHOP_BE.controller;

import com.example.SWP391_KOIFARMSHOP_BE.model.FeedbackResponse;
import com.example.SWP391_KOIFARMSHOP_BE.model.TransactionReponse;
import com.example.SWP391_KOIFARMSHOP_BE.model.TransactionRequest;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.Account;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.Transaction;
import com.example.SWP391_KOIFARMSHOP_BE.repository.IAccountRepository;
import com.example.SWP391_KOIFARMSHOP_BE.repository.TransactionRepository;
import com.example.SWP391_KOIFARMSHOP_BE.service.TransactionService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/transactions")
@CrossOrigin("*")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    IAccountRepository iAccountRepository;


    // API để tạo mới một transaction
    @PostMapping("/create")
    public ResponseEntity createTransaction(@RequestBody TransactionRequest transactionRequest) throws Exception {
        // Call service to create the VNPAY URL
        String vnPayUrl = transactionService.creatURL(transactionRequest);

        // Create the response object
        return ResponseEntity.ok(vnPayUrl);
    }

    @GetMapping("/all")
    public ResponseEntity<List<TransactionReponse>> getAllTransactions() {
        List<TransactionReponse> transactions = transactionService.getAllTransactions();
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }
    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<TransactionReponse>> getTransactionsByAccountId(@PathVariable String accountId) {
        List<TransactionReponse> transactions = transactionService.getTransactionsByAccountId(accountId);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }



    @PostMapping("/vnpay/response")
    public ResponseEntity handleVNPAYResponse(@RequestParam Map<String, String> params) {
        // In ra tất cả các tham số nhận được từ VNPAY
        System.out.println("Received params: " + params);

        String transactionID = params.get("vnp_TxnRef"); // Lấy mã giao dịch từ vnp_TxnRef
        String transactionStatus = params.get("vnp_ResponseCode"); // Lấy mã trạng thái giao dịch
        String amount = params.get("vnp_Amount"); // Lấy số tiền từ vnp_Amount

        // In ra các thông tin đã lấy
        System.out.println("Transaction ID: " + transactionID);
        System.out.println("Transaction Status: " + transactionStatus);
        System.out.println("Amount: " + amount);

        // Kiểm tra trạng thái giao dịch
        if ("00".equals(transactionStatus)) { // Giả sử "00" là mã trạng thái thành công
            // Tìm giao dịch và tài khoản khách hàng
            Transaction transaction = transactionRepository.findByTransactionID(transactionID);
            if (transaction != null) {
                String accountID = transaction.getAccountID();
                System.out.println("Found transaction. Account ID: " + accountID);

                Account account = iAccountRepository.findById(accountID)
                        .orElseThrow(() -> new EntityNotFoundException("Account not found with ID: " + accountID));

                // Cộng tiền vào ví
                float moneyToAdd = Float.parseFloat(amount) / 100; // Chia cho 100 vì VNPAY gửi số tiền tính bằng đồng
                System.out.println("Current Account Balance: " + account.getAccountBalance());
                account.setAccountBalance(account.getAccountBalance() + moneyToAdd);
                System.out.println("Updated Account Balance: " + account.getAccountBalance());

                // Lưu lại tài khoản
                iAccountRepository.save(account);

                // Trả về phản hồi thành công
                return ResponseEntity.ok("Tiền đã được nạp vào ví thành công.");
            } else {
                System.out.println("Transaction not found for ID: " + transactionID);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Giao dịch không tìm thấy.");
            }
        }

        System.out.println("Transaction failed with status: " + transactionStatus);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Giao dịch không thành công.");
    }

}
