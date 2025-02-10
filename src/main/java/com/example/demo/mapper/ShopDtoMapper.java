package com.example.demo.mapper;

import com.example.demo.dto.ShopDto;
import com.example.demo.entity.ShopOwner;

import java.sql.ResultSet;

public class ShopDtoMapper implements  RowMapper<ShopDto> {

    @Override
    public ShopDto mapRow(ResultSet rs) {
        ShopDto shopDto = new ShopDto();
        try {
            shopDto.setId(rs.getInt("shop_id"));
            shopDto.setName(rs.getString("shop_name"));
            shopDto.setRating(rs.getDouble("average_rating"));
            shopDto.setLogo(rs.getString("shop_logo"));
            shopDto.setProductActive(rs.getInt("product_active"));
            shopDto.setProductInactive(rs.getInt("product_inactive"));
            shopDto.setTaxNumber(rs.getString("tax_number"));
            shopDto.setCreateAt(rs.getString("create_at"));
            shopDto.setDecs(rs.getString("shop_desc"));

            ShopOwner shopOwner = new ShopOwner();
            shopOwner.setId(rs.getInt("shop_owner_id"));
            shopOwner.setName(rs.getString("shop_owner_name"));
            shopOwner.setEmail(rs.getString("shop_owner_email"));
            shopOwner.setIdentification(rs.getString("identification_number"));
            shopOwner.setFront(rs.getString("identification_image_front"));
            shopOwner.setBack(rs.getString("identification_image_back"));
            shopOwner.setEmail(rs.getString("shop_owner_email"));
            shopOwner.setProvince(rs.getString("shop_owner_province"));
            shopOwner.setDistrict(rs.getString("shop_owner_district"));
            shopOwner.setWard(rs.getString("shop_owner_ward"));

            shopDto.setOwner(shopOwner);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return shopDto;
    }
}
