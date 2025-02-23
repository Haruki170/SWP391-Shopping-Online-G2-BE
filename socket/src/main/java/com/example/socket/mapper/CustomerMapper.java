package com.example.socket.mapper;



import com.example.socket.entity.Customer;

import java.sql.ResultSet;

public class CustomerMapper implements RowMapper<Customer> {

    @Override
    public Customer mapRow(ResultSet rs) {
        Customer customer = new Customer();
        try {
            customer.setCustomerId(rs.getInt("customer_id"));
            customer.setEmail(rs.getString("customer_email"));
            customer.setName(rs.getString("customer_name"));
            customer.setAvatar(rs.getString("customer_avatar"));
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return customer;
    }
}
