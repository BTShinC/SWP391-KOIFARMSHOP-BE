package com.example.SWP391_KOIFARMSHOP_BE.api;


import com.example.SWP391_KOIFARMSHOP_BE.model.*;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.Account;
import com.example.SWP391_KOIFARMSHOP_BE.model.AccountResponse;
import com.example.SWP391_KOIFARMSHOP_BE.model.LoginRequest;
import com.example.SWP391_KOIFARMSHOP_BE.model.LoginResponse;
import com.example.SWP391_KOIFARMSHOP_BE.model.RegisterRequest;

import com.example.SWP391_KOIFARMSHOP_BE.service.AccountService;
import com.example.SWP391_KOIFARMSHOP_BE.service.AuthenticationService;
import com.example.SWP391_KOIFARMSHOP_BE.service.EmailService;
import com.example.SWP391_KOIFARMSHOP_BE.service.JwtBlacklistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class AuthenticationAPI {

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
     EmailService emailService;
    @Autowired
    AccountService accountService;
    @Autowired
    JwtBlacklistService jwtBlacklistService;
    // API đăng ký
    @PostMapping("Register")

    public ResponseEntity register(@Valid @RequestBody RegisterRequest registerRequest) {
        AccountResponse newAccount = authenticationService.register(registerRequest);
        emailService.sendSimpleMessage(registerRequest.getEmail(),
                "Registration successful",
                "Congratulations you have successfully registered with your username: " + registerRequest.getUserName() );

        return ResponseEntity.ok(newAccount);

    }

    // API đăng nhập
    @PostMapping("login")

    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest){
        String token = authenticationService.login(loginRequest); // Lấy token từ service
        AccountResponse accountResponse = authenticationService.getAccountDetails(loginRequest.getUserName()); // Lấy thông tin tài khoản

        // Trả về LoginResponse gồm token và thông tin tài khoản
        LoginResponse loginResponse = new LoginResponse(token, accountResponse);

        return ResponseEntity.ok(loginResponse);

    }

    // API lấy danh sách tài khoản
    @GetMapping("account")
    public ResponseEntity<List<AccountResponse>> getAllAccount() {
        List<AccountResponse> accounts = authenticationService.getAllAccount();
        return ResponseEntity.ok(accounts);
    }


    // API quên mật khẩu
    @PostMapping("/forgot")
    public String forgotPassword(@Valid @RequestBody PasswordResetRequest request) {
        System.out.println("Request received for email: " + request.getEmail());

        Account account = accountService.findByEmail(request.getEmail());
        if (account != null) {
            String token = UUID.randomUUID().toString();
            accountService.saveResetToken(request.getEmail(), token);

            String resetLink = "http://103.90.227.69/recoveryPassword?token=" + token;//gắn link FE vào
            emailService.sendSimpleMessage(request.getEmail(),
                    "Reset your password",
                    "Click the link to reset your password: " + resetLink);

            return "Password reset link has been sent to your email.";
        }

        System.out.println("Email address not found: " + request.getEmail());
        return "Email address not found.";
    }

    // API cài lại mật khẩu
    @PostMapping("/reset")
    public String resetPassword(@RequestBody PasswordResetToken tokenData) {
        // Kiểm tra token có hợp lệ hay không
        Account account = accountService.findByResetToken(tokenData.getToken());
        if (account != null) {
            // Cập nhật mật khẩu mới và xóa token
            account.setPassword(accountService.encode(tokenData.getNewPassword()));
            account.setResetToken(null);  // Xóa token sau khi dùng
            accountService.save(account);
            return "Password has been reset successfully.";
        }
        return "Invalid token.";
    }



}
