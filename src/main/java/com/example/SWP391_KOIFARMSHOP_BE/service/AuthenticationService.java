package com.example.SWP391_KOIFARMSHOP_BE.service;

import com.example.SWP391_KOIFARMSHOP_BE.exception.DuplicateEntity;
import com.example.SWP391_KOIFARMSHOP_BE.exception.EntityNotFoundException;
import com.example.SWP391_KOIFARMSHOP_BE.model.AccountResponse;
import com.example.SWP391_KOIFARMSHOP_BE.model.JwtTokenUtil;
import com.example.SWP391_KOIFARMSHOP_BE.model.LoginRequest;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.Account;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.RegisterRequest;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.Role;
import com.example.SWP391_KOIFARMSHOP_BE.repository.IAccountRepository;
import com.example.SWP391_KOIFARMSHOP_BE.repository.IRoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    IAccountRepository iAccountRepository;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    IRoleRepository iRoleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    AuthenticationManager authenticationManager;

    @Value("${default.role.name:USER}")
    private String defaultRoleName;

    public AccountResponse register(RegisterRequest registerRequest) {




        if (iAccountRepository.existsByEmail(registerRequest.getEmail())) {
            throw new DuplicateEntity("Email already exists");
        }


        Account account = modelMapper.map(registerRequest, Account.class);
        try {

            account.setPassword(passwordEncoder.encode(account.getPassword()));

            Role customerRole = iRoleRepository.findByRoleName("Customer")
                    .orElseThrow(() -> new RuntimeException("Role not found"));
            account.setRole(customerRole);  // Gán vai trò cho tài khoản

            Account newAccount = iAccountRepository.save(account);


            return modelMapper.map(newAccount, AccountResponse.class);

        } catch (Exception e) {
            System.err.println("Error while registering account: " + e.getMessage());
            throw new RuntimeException("Error occurred while registering the account", e);
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
            throw new UsernameNotFoundException("User not found with username: " + userName);
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
            throw new EntityNotFoundException("Account not found");
        }
        return modelMapper.map(account, AccountResponse.class);
    }
}
