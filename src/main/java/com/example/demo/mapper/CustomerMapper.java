package com.example.demo.mapper;

import com.example.demo.entity.Customer;

import java.sql.ResultSet;

public class CustomerMapper implements RowMapper<Customer> {

    @Override
    public Customer mapRow(ResultSet rs) {
        Customer customer = new Customer();
        try {
            customer.setId(rs.getInt("customer_id"));
            customer.setStatus(rs.getInt("status"));
            customer.setPhoneNumber(rs.getString("phone"));
            customer.setEmail(rs.getString("customer_email"));
            customer.setName(rs.getString("customer_name"));
            customer.setAvatar(rs.getString("customer_avatar"));
            customer.setPassword(rs.getString("customer_password"));
            customer.setCreate_at(rs.getString("create_at"));
            customer.setUpdate_at(rs.getString("update_at"));
            customer.setRefeshToken(rs.getString("refesh_token"));
            customer.setFogotPassword(rs.getString("fogot_password_code"));
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return customer;
    }
}
