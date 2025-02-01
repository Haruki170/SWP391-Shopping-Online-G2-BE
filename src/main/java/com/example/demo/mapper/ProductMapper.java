package com.example.demo.mapper;

import com.example.demo.entity.Category;
import com.example.demo.entity.Product;
import com.example.demo.entity.Shop;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductMapper implements RowMapper<Product> {
    @Override
    public Product mapRow(ResultSet rs) {
        Product product = new Product();
        try {
            product.setId(rs.getInt("product_id"));
            product.setName(rs.getString("product_name"));
            product.setAvatar(rs.getString("product_avatar"));
            product.setPrice((int) rs.getDouble("product_price"));
            product.setDescription(rs.getString("product_desc"));
            product.setStatus(rs.getInt("status"));
            product.setLength(rs.getDouble(("length")));
            product.setWeight(rs.getDouble("weight"));
            product.setWidth(rs.getDouble("width"));
            product.setHeight(rs.getDouble("height"));
            product.setUpdate_at(rs.getString("update_at"));
            product.setCreate_at(rs.getString("create_at"));
            //lay category
            List<Category> categories = new ArrayList<>();
            if (rs.getInt("category_id") > 0) {
                Category category = new Category();
                category.setId(rs.getInt("category_id"));
                category.setName(rs.getString("category_name"));
                categories.add(category);
            }
            product.setCategories(categories);
            //lay shop
            Shop shop = new Shop();
            shop.setId(rs.getInt("shop_id"));
            shop.setName(rs.getString("shop_name"));
//            shop.setAddress(rs.getString("shop_address"));
            shop.setDescription(rs.getString("shop_desc"));
            //them shop vao product
            product.setShop(shop);
        }catch (Exception e) {
            System.out.println(e);
        }
        return product;
    }
}
