package com.example.SWP391_KOIFARMSHOP_BE.pojo;

import jakarta.persistence.*;

@Entity
@Table(name = "Role")
public class Role {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "Role_ID")
    private long roleID;
    @Column(name = "Role_Name")
    private String roleName;

    public long getRoleID() {
        return roleID;
    }

    public void setRoleID(long roleID) {
        this.roleID = roleID;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Role() {
    }

    public Role(long roleID, String roleName) {
        this.roleID = roleID;
        this.roleName = roleName;
    }
}
