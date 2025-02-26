package com.example.socket.mapper;

import com.example.socket.entity.Shop;

import java.sql.ResultSet;

public class ShopMapper implements RowMapper<Shop> {
    @Override
    public Shop mapRow(ResultSet rs) {
        Shop shop = new Shop();
        try {
            shop.setId(rs.getInt("shop_id"));
            shop.setName(rs.getString("shop_name"));
            shop.setAvatar(rs.getString("shop_logo"));
        }catch (Exception e) {
            e.printStackTrace();
        }
        return shop;
    }
}
