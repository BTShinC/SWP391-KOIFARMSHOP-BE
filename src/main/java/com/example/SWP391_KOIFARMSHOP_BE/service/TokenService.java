package com.example.SWP391_KOIFARMSHOP_BE.service;

import com.example.SWP391_KOIFARMSHOP_BE.pojo.Account;
import com.example.SWP391_KOIFARMSHOP_BE.repository.IAccountRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class TokenService {

    @Autowired
    IAccountRepository iAccountRepository;

    // Secret key để ký và xác minh JWT token
    private final String SECRET_KEY = "phanngocghoangsang1234phanngochoangsang456phanngochoangsang7890";

    // Lấy secret key cho việc ký JWT
    private SecretKey getSigninKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    // Tạo JWT token từ thông tin của Account
    public String generateToken(Account account) {
        return Jwts.builder()
                .setSubject(String.valueOf(account.getAccountID())) // Lưu account ID vào token
                .setIssuedAt(new Date(System.currentTimeMillis())) // Thời gian phát hành
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // Hết hạn sau 1 ngày
                .signWith(getSigninKey()) // Ký token bằng secret key
                .compact();
    }

    // Xác minh và lấy thông tin Account từ JWT token
    public Account getAccountByToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigninKey()) // Thiết lập secret key để xác minh
                .build()
                .parseClaimsJws(token) // Xác minh và phân tích JWT token
                .getBody();

        String idString = claims.getSubject(); // Lấy ID từ token
        long id = Long.parseLong(idString); // Chuyển ID sang kiểu long
        return iAccountRepository.findAccountByaccountID(id); // Tìm Account trong database dựa trên ID
    }
}
