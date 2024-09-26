package com.example.SWP391_KOIFARMSHOP_BE.service;



import com.example.SWP391_KOIFARMSHOP_BE.pojo.Role;

import java.util.List;
import java.util.Optional;

public interface IRoleService {
    public List<Role> getAllRole();
    public Role insertRole(Role role);
    public Role updateRole(long roleID, Role role);
    public void deleteRole (long roleID);
    public Optional<Role> getRoleByID(long roleID);
}
