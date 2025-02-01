package com.example.demo.mapper;

import com.example.demo.entity.ProductImage;

import java.sql.ResultSet;

public class ProductImageMapper implements RowMapper<ProductImage> {

    @Override
    public ProductImage mapRow(ResultSet rs) {
        ProductImage productImage = new ProductImage();
        try {
            productImage.setId(rs.getInt("product_image_id"));
            productImage.setImage(rs.getString("product_image"));
        }catch (Exception e) {
            e.printStackTrace();
        }
        return productImage;
    }
}
