package com.example.SWP391_KOIFARMSHOP_BE.service;

import com.example.SWP391_KOIFARMSHOP_BE.exception.DuplicateEntity;
import com.example.SWP391_KOIFARMSHOP_BE.exception.EntityNotFoundException;
import com.example.SWP391_KOIFARMSHOP_BE.model.AccountResponse;
import com.example.SWP391_KOIFARMSHOP_BE.model.LoginRequest;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.Account;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.RegisterRequest;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.Role;
import com.example.SWP391_KOIFARMSHOP_BE.repository.IAccountRepository;
import com.example.SWP391_KOIFARMSHOP_BE.repository.IRoleRepository;
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

    public AccountResponse register(RegisterRequest registerRequest) {
        if (iAccountRepository.existsByEmail(registerRequest.getEmail())) {
            throw new DuplicateEntity("Email already exists");
        }

        // Tạo tài khoản mới và mã hóa mật khẩu
        Account account = modelMapper.map(registerRequest, Account.class);
        account.setPassword(passwordEncoder.encode(account.getPassword()));

        // Lưu tài khoản vào cơ sở dữ liệu lần đầu tiên để có accountID
        Account newAccount = iAccountRepository.save(account);

        // Kiểm tra nếu đây là tài khoản đầu tiên (accountID == 1) thì set role Admin
        if (newAccount.getAccountID() == 1) {
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




//    public AccountResponse register(RegisterRequest registerRequest) {
//
//        if (iAccountRepository.existsByEmail(registerRequest.getEmail())) {
//            throw new DuplicateEntity("Email already exists");
//        }
////        if (iAccountRepository.existsByPhone(registerRequest.getPhoneNumber())) {
////            throw new DuplicateEntity("Email already exists");
////        }
//
//        Account account = modelMapper.map(registerRequest, Account.class);
//        try {
//
//            account.setPassword(passwordEncoder.encode(account.getPassword()));
//            Account newAccount = iAccountRepository.save(account);
//            return modelMapper.map(newAccount, AccountResponse.class);
//
//        } catch (Exception e) {
////            if (e.getMessage().contains(account.getEmail())) {
////                throw new DuplicateEntity("Duplicate Email");
////            }
////            throw new DuplicateEntity("Duplicate phone");
//            throw new RuntimeException("Error occurred while registering the account", e);
//        }
//
//    }

    // Hàm xử lý đăng nhập
    public AccountResponse login(LoginRequest loginRequest){
        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUserName(),
                            loginRequest.getPassword()
                    )
            );

            // Xác thực thành công, lấy tài khoản và trả về
            Account account = (Account) authentication.getPrincipal();
            return modelMapper.map(account, AccountResponse.class);

        } catch (Exception e) {
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
}
