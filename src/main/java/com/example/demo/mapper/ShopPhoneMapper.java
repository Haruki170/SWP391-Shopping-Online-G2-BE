package com.example.demo.mapper;

import com.example.demo.entity.ShopPhone;

import java.sql.ResultSet;

public class ShopPhoneMapper implements RowMapper<ShopPhone> {
    @Override
    public ShopPhone mapRow(ResultSet rs) {
        ShopPhone shopPhone = new ShopPhone();
        try {
            shopPhone.setId(rs.getInt("shop_phone_id"));
            shopPhone.setPhoneNumber(rs.getString("shop_phone_number"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return shopPhone;
    }
}
