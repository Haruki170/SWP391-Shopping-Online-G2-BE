package com.example.demo.mapper;

import com.example.demo.entity.Shop;

import java.sql.ResultSet;

public class ShopMapper implements RowMapper<Shop> {
    @Override
    public Shop mapRow(ResultSet rs) {
        Shop shop = new Shop();
        try {
            shop.setId(rs.getInt("shop_id"));
            shop.setName(rs.getString("shop_name"));
            shop.setDescription(rs.getString("shop_desc"));
            shop.setLogo(rs.getString("shop_logo"));
            shop.setStatus(rs.getInt("status"));
            shop.setAutoShipCost(rs.getInt("automatic_shipping_cost"));
            shop.setCreate_at(rs.getString("create_at"));
            shop.setUpdate_at(rs.getString("update_at"));
        }catch (Exception e) {
            e.printStackTrace();
        }
        return shop;
    }
}
