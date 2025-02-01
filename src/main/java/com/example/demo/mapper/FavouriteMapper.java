package com.example.demo.mapper;

import com.example.demo.entity.Favourite;

import java.sql.ResultSet;

public class FavouriteMapper implements RowMapper<Favourite> {
    @Override
    public Favourite mapRow(ResultSet rs) {
        Favourite favourite = new Favourite();
        try {
            favourite.setId(rs.getInt("wishlist_id"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return favourite;
    }
}


