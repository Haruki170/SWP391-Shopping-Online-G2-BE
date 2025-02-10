package com.example.demo.mapper;

import com.example.demo.entity.Shop;
import com.example.demo.entity.ShopOwner;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ShopOwnerMapper implements RowMapper<ShopOwner> {
    @Override
    public ShopOwner mapRow(ResultSet rs) {
        ShopOwner shopOwner = new ShopOwner();
        try {
            shopOwner.setId(rs.getInt("shop_owner_id"));
            shopOwner.setEmail(rs.getString("shop_owner_email"));
            shopOwner.setProvince(rs.getString("shop_owner_province"));
            shopOwner.setDistrict(rs.getString("shop_owner_district"));
            shopOwner.setWard(rs.getString("shop_owner_ward"));
            shopOwner.setIdentification(rs.getString("identification_number"));
            shopOwner.setPassword(rs.getString("shop_owner_password"));
            shopOwner.setAddress(rs.getString("shop_owner_address"));
            shopOwner.setPhone(rs.getString("shop_owner_phone_number"));
            shopOwner.setStatus(rs.getInt("status"));
            shopOwner.setCreate_at(rs.getString("create_at"));
            shopOwner.setUpdate_at(rs.getString("update_at"));
            shopOwner.setAvatar(rs.getString("shop_owner_avatar"));
            shopOwner.setName(rs.getString("shop_owner_name"));
            shopOwner.setFogotPassword(rs.getString("fogot_password_code"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return shopOwner;
    }
}

