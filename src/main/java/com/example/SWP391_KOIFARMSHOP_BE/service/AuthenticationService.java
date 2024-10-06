package com.example.SWP391_KOIFARMSHOP_BE.service;

import com.example.SWP391_KOIFARMSHOP_BE.exception.DuplicateEntity;
import com.example.SWP391_KOIFARMSHOP_BE.exception.EntityNotFoundException;
import com.example.SWP391_KOIFARMSHOP_BE.model.AccountResponse;
import com.example.SWP391_KOIFARMSHOP_BE.config.JwtTokenUtil;
import com.example.SWP391_KOIFARMSHOP_BE.model.LoginRequest;
import com.example.SWP391_KOIFARMSHOP_BE.model.RegisterRequest;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.Account;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.Role;
import com.example.SWP391_KOIFARMSHOP_BE.repository.IAccountRepository;
import com.example.SWP391_KOIFARMSHOP_BE.repository.IRoleRepository;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    IAccountRepository iAccountRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    IRoleRepository iRoleRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    public AccountResponse register(@Valid RegisterRequest registerRequest) {
        if (iAccountRepository.existsByEmail(registerRequest.getEmail())) {
            throw new DuplicateEntity("Email already exists");
        }

        // Tạo tài khoản mới và mã hóa mật khẩu
        Account account = modelMapper.map(registerRequest, Account.class);
        account.setPassword(passwordEncoder.encode(account.getPassword()));

        // Sinh ID mới cho tài khoản
        String nextId = generateNextAccountId();
        account.setAccountID(nextId);  // Đặt ID cho tài khoản mới

        // Lưu tài khoản vào cơ sở dữ liệu lần đầu tiên
        Account newAccount = iAccountRepository.save(account);

        // Kiểm tra nếu tài khoản có accountID là "A001" thì set role Admin
        if (newAccount.getAccountID().equals("A001")) {
            Role adminRole = iRoleRepository.findByRoleName("admin")
                    .orElseThrow(() -> new RuntimeException("Admin role not found"));
            newAccount.setRole(adminRole);
        } else {
            // Gán role mặc định là Customer cho các tài khoản khác
            Role customerRole = iRoleRepository.findByRoleName("customer")
                    .orElseThrow(() -> new RuntimeException("Customer role not found"));
            newAccount.setRole(customerRole);
        }

        // Cập nhật tài khoản với vai trò mới
        newAccount = iAccountRepository.save(newAccount);

        return modelMapper.map(newAccount, AccountResponse.class);
    }

    private String generateNextAccountId() {
        // Lấy tài khoản có accountID lớn nhất hiện tại
        Account lastAccount = iAccountRepository.findTopByOrderByAccountIDDesc();

        // Nếu có tài khoản trước đó, sinh ID mới
        if (lastAccount != null) {
            String lastId = lastAccount.getAccountID(); // Ví dụ: "A001"
            int idNumber = Integer.parseInt(lastId.substring(1)); // Lấy phần số từ "A001"
            String nextId = String.format("A%03d", idNumber + 1); // Sinh ID mới theo định dạng "A002"
            return nextId;
        } else {
            return "A001"; // Nếu chưa có tài khoản nào, bắt đầu từ "A001"
        }
    }



    // Hàm xử lý đăng nhập
    public String login(LoginRequest loginRequest) {
        try {
            // Xác thực thông tin đăng nhập
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUserName(),
                            loginRequest.getPassword()
                    )
            );

            // Nếu xác thực thành công, lấy thông tin tài khoản
            Account account = (Account) authentication.getPrincipal();

            // Tạo JWT Token
            String token = jwtTokenUtil.generateToken(account.getUsername());

            // Trả về JWT Token cho người dùng
            return token;

        } catch (Exception e) {
            System.err.println("Error : " + e.getMessage());
            throw new EntityNotFoundException("Username or password is invalid");
        }
    }

    // Tải thông tin người dùng từ tên người dùng
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Account account = iAccountRepository.findAccountByUserName(userName);
        if (account == null) {
            throw new UsernameNotFoundException("Account with username " + userName + " not found");
        }
        return account;
    }

    // Lấy danh sách tất cả tài khoản
    public List<AccountResponse> getAllAccount(){
        List<Account> accounts = iAccountRepository.findAll();

        // Chuyển đổi từ Account sang AccountResponse bằng cách sử dụng ModelMapper
        return accounts.stream()
                .map(account -> modelMapper.map(account, AccountResponse.class))
                .collect(Collectors.toList());
    }
    // Hàm lấy chi tiết tài khoản theo username
    public AccountResponse getAccountDetails(String username) {
        Account account = iAccountRepository.findAccountByUserName(username);
        if (account == null) {
            throw new EntityNotFoundException("Account with username " + username + " not found");
        }
        return modelMapper.map(account, AccountResponse.class);
    }

    // Hàm để lấy thời gian hết hạn của token
    public long getTokenExpiration(String token) {
        // Sử dụng JwtTokenUtil để lấy thời gian hết hạn của token
        Date expirationDate = jwtTokenUtil.extractExpiration(token);
        return expirationDate.getTime(); // Trả về thời gian hết hạn dưới dạng milliseconds
    }
}
