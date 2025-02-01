package com.example.demo.mapper;

import com.example.demo.entity.Cart;

import java.sql.ResultSet;

public class CartMapper implements RowMapper<Cart> {
    @Override
    public Cart mapRow(ResultSet rs) {
        Cart cart = new Cart();
        try {
            cart.setId(rs.getInt("cart_id"));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return cart;
    }
}
