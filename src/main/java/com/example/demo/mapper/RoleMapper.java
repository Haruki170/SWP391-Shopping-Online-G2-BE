package com.example.demo.mapper;

import com.example.demo.entity.Role;

import java.sql.ResultSet;

public class RoleMapper implements RowMapper<Role> {
    @Override
    public Role mapRow(ResultSet rs) {
        Role role = new Role();
        try {
            role.setId(rs.getInt("role_id"));
            role.setName(rs.getString("role_name"));
            role.setCreate_at(rs.getString("create_at"));
            role.setUpdate_at(rs.getString("update_at"));
        }catch (Exception e) {
            e.printStackTrace();
        }
        return role;
    }
}
