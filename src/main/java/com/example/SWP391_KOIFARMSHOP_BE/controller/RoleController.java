package com.example.SWP391_KOIFARMSHOP_BE.controller;



import com.example.SWP391_KOIFARMSHOP_BE.model.RoleRequest;
import com.example.SWP391_KOIFARMSHOP_BE.model.RoleResponse;

import com.example.SWP391_KOIFARMSHOP_BE.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/role")
public class RoleController {
    @Autowired
    RoleService roleService;


    // Create Role
    @PostMapping("post")
    public ResponseEntity<RoleResponse> createRole(@Valid @RequestBody RoleRequest roleRequest) {
        RoleResponse newRole = roleService.createRole(roleRequest);
        return ResponseEntity.ok(newRole);
    }

    // Get All Roles
    @GetMapping
    public ResponseEntity<List<RoleResponse>> getAllRoles() {
        List<RoleResponse> roles = roleService.getAllRoles();
        return ResponseEntity.ok(roles);
    }

    // Update Role
    @PutMapping("/{id}")
    public ResponseEntity<RoleResponse> updateRole(@PathVariable String id, @Valid @RequestBody RoleRequest roleRequest) {
        RoleResponse updatedRole = roleService.updateRole(id, roleRequest);
        return ResponseEntity.ok(updatedRole);
    }

    // Delete Role
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRole(@PathVariable String id) {
        roleService.deleteRole(id);

    }


}
