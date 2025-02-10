package com.example.demo.mapper;

import com.example.demo.entity.Customer;
import com.example.demo.entity.ShopRegister;

import java.sql.ResultSet;
import java.util.Date;

public class ShopRegisterMapper implements RowMapper<ShopRegister> {
    @Override
    public ShopRegister mapRow(ResultSet rs) {
        ShopRegister shopRegister = new ShopRegister();
        try {
            shopRegister.setId(rs.getInt("id"));
            shopRegister.setName(rs.getString("name"));
            shopRegister.setAddress(rs.getString("address"));
            shopRegister.setPhone(rs.getString("phone"));
            shopRegister.setDesc(rs.getString("descriptions"));
            shopRegister.setStatus(rs.getInt("status"));
            shopRegister.setIsOnline(rs.getInt("isOnline"));
            shopRegister.setTaxNumber(rs.getString("tax_number"));
            shopRegister.setCreate_at(rs.getString("created_at"));

            Customer customer = new Customer();
            customer.setId(rs.getInt("customer_id"));
            customer.setEmail(rs.getString("customer_email"));
            shopRegister.setCustomer(customer);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return shopRegister;
    }
}
