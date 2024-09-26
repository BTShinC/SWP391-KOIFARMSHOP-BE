package com.example.SWP391_KOIFARMSHOP_BE.service;

import com.example.SWP391_KOIFARMSHOP_BE.pojo.Role;
import com.example.SWP391_KOIFARMSHOP_BE.repository.IRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class RoleService implements IRoleService{
    @Autowired
    private IRoleRepository iRoleRepository;
    @Override
    public List<Role> getAllRole() {
        return iRoleRepository.findAll();
    }

    @Override
    public Role insertRole(Role role) {
        return iRoleRepository.save(role);
    }

    @Override
    public Role updateRole(long roleID, Role role) {
        Role r = iRoleRepository.getById(roleID);
        if(r != null){
            r.setRoleName(role.getRoleName());
            return iRoleRepository.save(r);
        }
        return null;
    }

    @Override
    public void deleteRole(long roleID) {
        iRoleRepository.deleteById(roleID);
    }

    @Override
    public Optional<Role> getRoleByID(long roleID) {
        return iRoleRepository.findById(roleID);
    }
}
