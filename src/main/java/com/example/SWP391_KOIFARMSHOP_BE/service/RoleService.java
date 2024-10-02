package com.example.SWP391_KOIFARMSHOP_BE.service;

import com.example.SWP391_KOIFARMSHOP_BE.exception.EntityNotFoundException;
import com.example.SWP391_KOIFARMSHOP_BE.model.ProductRequest;
import com.example.SWP391_KOIFARMSHOP_BE.model.ProductResponse;
import com.example.SWP391_KOIFARMSHOP_BE.model.RoleRequest;
import com.example.SWP391_KOIFARMSHOP_BE.model.RoleResponse;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.Product;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.Role;
import com.example.SWP391_KOIFARMSHOP_BE.repository.IRoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoleService {
    @Autowired
    private IRoleRepository iRoleRepository;

    @Autowired
    ModelMapper modelMapper;

    public RoleResponse createRole(RoleRequest rolerequest) {

        try {
            // Kiểm tra xem Role đã tồn tại chưa
            Optional<Role> existingRole = iRoleRepository.findByRoleName(rolerequest.getRoleName());

            if (existingRole.isPresent()) {
                // Nếu Role đã tồn tại, trả về thông báo lỗi
                throw new IllegalArgumentException("Role with name '" + rolerequest.getRoleName() + "' already exists.");
            }

            // Nếu Role chưa tồn tại, tiếp tục lưu nó
            Role role = modelMapper.map(rolerequest, Role.class);
            Role savedRole = iRoleRepository.save(role);
            return modelMapper.map(savedRole, RoleResponse.class);
        } catch (IllegalArgumentException e) {
            // Trả về lỗi do trùng Role
            System.err.println("Error: " + e.getMessage());
            throw new IllegalArgumentException(e.getMessage());
        } catch (Exception e) {
            // Xử lý các lỗi khác
            System.err.println("Error: " + e.getMessage());
            throw new EntityNotFoundException("Error when trying to create Role.");
        }

    }

    public List<RoleResponse> getAllRoles() {
        try {
            List<Role> roles = iRoleRepository.findAll();
            return roles.stream()
                    .map(role -> modelMapper.map(role, RoleResponse.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            throw new EntityNotFoundException("Error when fetching roles");
        }
    }




}
