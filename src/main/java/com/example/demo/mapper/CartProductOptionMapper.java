package com.example.demo.mapper;

import com.example.demo.entity.ProductOption;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
@Component
public class CartProductOptionMapper implements RowMapper<ProductOption> {

    @Override
    public ProductOption mapRow(ResultSet rs) {
        ProductOption productOption = new ProductOption();
        try {
            productOption.setId(rs.getInt("product_option_id"));
            productOption.setName(rs.getString("product_option_name"));
        }catch (Exception e) {
            e.printStackTrace();
        }
        return productOption;
    }
}
