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

    private ModelMapper modelMapper;

    // Tạo Role mới
    public RoleResponse createRole(RoleRequest roleRequest) {
        Optional<Role> existingRole = iRoleRepository.findByRoleName(roleRequest.getRoleName());

        if (existingRole.isPresent()) {
            throw new IllegalArgumentException("Role with name '" + roleRequest.getRoleName() + "' already exists.");
        }
        String nextId = generateNextRoleId();
        Role role = modelMapper.map(roleRequest, Role.class);
        role.setRoleID(nextId);
        Role savedRole = iRoleRepository.save(role);
        return modelMapper.map(savedRole, RoleResponse.class);
    }

    private String generateNextRoleId() {
        Role lastRole = iRoleRepository.findTopByOrderByRoleIDDesc();
        if (lastRole != null) {
            String lastId = lastRole.getRoleID();
            int idNumber = Integer.parseInt(lastId.substring(1));
            String nextId = String.format("R%03d", idNumber + 1);
            return nextId;
        } else {
            return "R001";
        }
    }

    // Chỉnh sửa Role
    public RoleResponse updateRole(String id, RoleRequest roleRequest) {
        Role existingRole = iRoleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Role with ID " + id + " not found"));

        // Cập nhật thông tin Role
        existingRole.setRoleName(roleRequest.getRoleName());

        Role updatedRole = iRoleRepository.save(existingRole);
        return modelMapper.map(updatedRole, RoleResponse.class);
    }

    // Xóa Role
    public void deleteRole(String id) {
        Role existingRole = iRoleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Role with ID " + id + " not found"));

        iRoleRepository.delete(existingRole);
    }

    // Xem tất cả Roles
    public List<RoleResponse> getAllRoles() {
        List<Role> roles = iRoleRepository.findAll();
        return roles.stream()
                .map(role -> modelMapper.map(role, RoleResponse.class))
                .collect(Collectors.toList());

    }




}
