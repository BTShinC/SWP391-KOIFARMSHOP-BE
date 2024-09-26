package com.example.SWP391_KOIFARMSHOP_BE.controller;


import com.example.SWP391_KOIFARMSHOP_BE.pojo.Role;
import com.example.SWP391_KOIFARMSHOP_BE.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/role")
public class RoleController {
    @Autowired
    private IRoleService iRoleService;
    @GetMapping("/")
    public ResponseEntity<List<Role>> fetchALlRole(){
        return ResponseEntity.ok(iRoleService.getAllRole());
    }
    @PostMapping("/")
    @ResponseStatus (HttpStatus.CREATED)
    public Role saveRole(@RequestBody Role role){
        return iRoleService.insertRole(role);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Role> updateRole(@PathVariable long id, @RequestBody Role role){
        Role updateRole = iRoleService.updateRole(id, role);
        return ResponseEntity.ok(updateRole);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRole(@PathVariable long id){
        iRoleService.deleteRole(id);
        return ResponseEntity.ok("Delete Role success!");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Role>> getRoleByID(@PathVariable long id){
        Optional<Role> role = iRoleService.getRoleByID(id);
        return  ResponseEntity.ok(role);
    }
}
