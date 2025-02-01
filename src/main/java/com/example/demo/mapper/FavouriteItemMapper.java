package com.example.demo.mapper;

import com.example.demo.entity.Favourite;
import com.example.demo.entity.Favourite_item;
import com.example.demo.entity.Product;

import java.sql.ResultSet;

public class FavouriteItemMapper implements RowMapper<Favourite_item> {
    @Override
    public Favourite_item mapRow(ResultSet rs) {
        Favourite_item favourite_item = new Favourite_item();
        try {
            favourite_item.setId(rs.getInt("wishlist_item_id"));

            Product p = new Product();
            p.setId(rs.getInt("product_id"));
            p.setName(rs.getString("product_name"));
            p.setPrice((int) rs.getDouble("product_price"));
            p.setAvatar(rs.getString("product_avatar"));
            p.setDescription(rs.getString("product_desc"));

            favourite_item.setProduct(p);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return favourite_item;
    }
}
