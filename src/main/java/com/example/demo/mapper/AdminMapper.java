package com.example.demo.mapper;

import com.example.demo.entity.Admin;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminMapper implements RowMapper<Admin> {

    @Override
    public Admin mapRow(ResultSet rs) {
        Admin admin = new Admin();
        try {
            admin.setId(rs.getInt("admin_id"));
            admin.setEmail(rs.getString("admin_email"));
            admin.setPassword(rs.getString("admin_password"));
            admin.setName(rs.getString("admin_name"));
            admin.setPhone(rs.getString("admin_phone"));
            admin.setAddress(rs.getString("admin_address"));
            admin.setDistrict(rs.getString("admin_district"));
            admin.setWard(rs.getString("admin_ward"));
            admin.setProvince(rs.getString("admin_province"));
            admin.setCreate_at(rs.getString("create_at"));
            admin.setStatus(rs.getInt("status"));
            admin.setUpdate_at(rs.getString("update_at"));
            admin.setFogotPassword(rs.getString("fogot_password_code"));
            admin.setRole(rs.getInt("role"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return admin;
    }
}
