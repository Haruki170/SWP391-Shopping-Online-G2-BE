package com.example.demo.mapper;

import com.example.demo.entity.Shipper;

import java.sql.ResultSet;

public class ShipperMapper implements RowMapper<Shipper> {

    @Override
    public Shipper mapRow(ResultSet rs) {
        Shipper customer = new Shipper();
        try {
            customer.setId(rs.getInt("shipper_id"));
            customer.setStatus(rs.getInt("status"));
            customer.setPhone(rs.getString("phone"));
            customer.setIdentity(rs.getString("shipper_identity"));
            customer.setName(rs.getString("shipper_name"));
            customer.setAddress(rs.getString("address"));
            customer.setPassword(rs.getString("shipper_password"));
            customer.setBirthday(rs.getString("birthday"));
            customer.setAvatar(rs.getString("shipper_avatar"));
            customer.setCreate_at(rs.getString("create_at"));
            customer.setUpdate_at(rs.getString("update_at"));
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return customer;
    }
}