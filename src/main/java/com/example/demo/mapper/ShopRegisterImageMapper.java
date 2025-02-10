package com.example.demo.mapper;

import com.example.demo.entity.ShopRegisterImage;

import java.sql.ResultSet;

public class ShopRegisterImageMapper implements RowMapper<ShopRegisterImage> {
    @Override
    public ShopRegisterImage mapRow(ResultSet rs) {
        ShopRegisterImage shopRegisterImage = new ShopRegisterImage();
        try {
            shopRegisterImage.setId(rs.getInt("id"));
            shopRegisterImage.setImage(rs.getString("image"));
        }catch (Exception e) {
            e.printStackTrace();
        }
        return shopRegisterImage;
    }
}
