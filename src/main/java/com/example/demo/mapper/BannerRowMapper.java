package com.example.demo.mapper;

import com.example.demo.entity.Banner;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BannerRowMapper implements RowMapper<Banner> {
    @Override
    public Banner mapRow(ResultSet rs)  {
        try {
            return new Banner(
                    rs.getInt("id"),
                    rs.getString("image"),
                    rs.getString("description")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
