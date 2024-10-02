package com.example.SWP391_KOIFARMSHOP_BE.controller;


import com.example.SWP391_KOIFARMSHOP_BE.model.AccountResponse;
import com.example.SWP391_KOIFARMSHOP_BE.model.RegisterRequest;
import com.example.SWP391_KOIFARMSHOP_BE.model.RoleRequest;
import com.example.SWP391_KOIFARMSHOP_BE.model.RoleResponse;
import com.example.SWP391_KOIFARMSHOP_BE.pojo.Role;
import com.example.SWP391_KOIFARMSHOP_BE.service.IRoleService;
import com.example.SWP391_KOIFARMSHOP_BE.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/role")
public class RoleController {
    @Autowired
    RoleService roleService;


    @PostMapping("Create")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity CreatRole(@Valid @RequestBody RoleRequest roleRequest) {
        RoleResponse newRole = roleService.createRole(roleRequest);
        return ResponseEntity.ok(newRole);
    }
    @GetMapping
    public ResponseEntity<List<RoleResponse>> getAllRoles() {
        List<RoleResponse> roles = roleService.getAllRoles();
        return ResponseEntity.ok(roles);
    }


}
