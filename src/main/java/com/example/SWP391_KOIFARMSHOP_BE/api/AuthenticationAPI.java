package com.example.SWP391_KOIFARMSHOP_BE.api;

import com.example.SWP391_KOIFARMSHOP_BE.model.AccountResponse;
import com.example.SWP391_KOIFARMSHOP_BE.model.LoginRequest;
import com.example.SWP391_KOIFARMSHOP_BE.model.LoginResponse;
import com.example.SWP391_KOIFARMSHOP_BE.model.RegisterRequest;
import com.example.SWP391_KOIFARMSHOP_BE.service.AuthenticationService;
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

@RestController
@RequestMapping("/api")
public class AuthenticationAPI {

    @Autowired
    AuthenticationService authenticationService;


    @Autowired
    JwtBlacklistService jwtBlacklistService;

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

//    @PostMapping("logout")
//    public ResponseEntity<String> logout(@RequestHeader("Authorization") String token) {
//        // Loại bỏ "Bearer " ở đầu token
//        String jwtToken = token.substring(7);
//
//        // Tính toán thời gian hết hạn của token (lấy expiration từ token)
//        long expirationTime = authenticationService.getTokenExpiration(jwtToken);
//
//        // Thêm vào blacklist
//        jwtBlacklistService.blacklistToken(jwtToken, expirationTime);
//
//        return ResponseEntity.ok("Logged out successfully.");
//    }

    @PostMapping("logout")
    @Operation(summary = "Logout user", description = "Logs out the user by invalidating the JWT token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully logged out"),
            @ApiResponse(responseCode = "400", description = "Invalid or missing Authorization header")
    })
    public ResponseEntity<String> logout(
            @Parameter(in = ParameterIn.HEADER, required = true, description = "JWT token with Bearer prefix")
            @RequestHeader(value = "Authorization", required = false) String token
    ) {
        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("Invalid or missing Authorization header");
        }

        // Loại bỏ "Bearer " ở đầu token
        String jwtToken = token.substring(7);

        // Tính toán thời gian hết hạn của token (lấy expiration từ token)
        long expirationTime = authenticationService.getTokenExpiration(jwtToken);

        // Thêm vào blacklist
        jwtBlacklistService.blacklistToken(jwtToken, expirationTime);

        return ResponseEntity.ok("Logged out successfully.");
    }
}
