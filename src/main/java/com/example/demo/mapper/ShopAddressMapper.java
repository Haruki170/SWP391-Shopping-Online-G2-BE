package com.example.demo.mapper;

import com.example.demo.entity.ShopAddress;

import java.sql.ResultSet;

public class ShopAddressMapper implements RowMapper<ShopAddress> {
    @Override
    public ShopAddress mapRow(ResultSet rs) {
        ShopAddress shopAddress = new ShopAddress();
        try {
            shopAddress.setId(rs.getLong("shop_address_id"));
            shopAddress.setProvince(rs.getString("shop_address_province"));
            shopAddress.setDistrict(rs.getString("shop_address_district"));
            shopAddress.setWard(rs.getString("shop_address_ward"));
            shopAddress.setAddressDetail(rs.getString("shop_address_detail"));
        }catch (Exception e) {
            e.printStackTrace();
        }
        return shopAddress;
    }
}
