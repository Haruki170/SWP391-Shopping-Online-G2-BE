package com.example.demo.mapper;

import com.example.demo.entity.ProductOption;

import java.sql.ResultSet;

public class ProductOptionMapper implements RowMapper<ProductOption>{
    @Override
    public ProductOption mapRow(ResultSet rs) {
        ProductOption productOption = new ProductOption();
        try {
            productOption.setId(rs.getInt("product_option_id"));
            productOption.setName(rs.getString("product_option_name"));
            productOption.setQuantity(rs.getInt("quantity"));

        }catch (Exception e) {
            e.printStackTrace();
        }
        return productOption;
    }

}
