package com.example.SWP391_KOIFARMSHOP_BE.service;

import com.example.SWP391_KOIFARMSHOP_BE.pojo.Account;
import com.example.SWP391_KOIFARMSHOP_BE.repository.IAccountRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
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
                .claim("role", account.getRole().getRoleName()) // Thêm vai trò vào token
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

        // Lấy ID từ token
        String idString = claims.getSubject();

        // Lấy vai trò từ token
        String role = claims.get("role", String.class);

//        // Kiểm tra vai trò, chỉ chấp nhận Admin hoặc Staff
        if (!role.equals("Admin") && !role.equals("Staff")) {
            throw new RuntimeException("Unauthorized: Access denied for role: " + role);
        }

        // Nếu vai trò hợp lệ, tìm tài khoản trong cơ sở dữ liệu
        System.out.println(role);
        return iAccountRepository.findAccountByaccountID(idString);
    }

}
