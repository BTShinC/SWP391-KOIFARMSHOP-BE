package com.example.SWP391_KOIFARMSHOP_BE.api;

import com.example.SWP391_KOIFARMSHOP_BE.model.AccountResponse;
import com.example.SWP391_KOIFARMSHOP_BE.model.LoginRequest;
import com.example.SWP391_KOIFARMSHOP_BE.model.LoginResponse;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.RegisterRequest;
import com.example.SWP391_KOIFARMSHOP_BE.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AuthenticationAPI {

    @Autowired
    AuthenticationService authenticationService;
    @PostMapping("Register")
    public ResponseEntity register(@Valid @RequestBody RegisterRequest registerRequest) {
        AccountResponse newAccount = authenticationService.register(registerRequest);
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
}
