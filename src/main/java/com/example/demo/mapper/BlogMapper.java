package com.example.demo.mapper;

import com.example.demo.entity.Blog;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BlogMapper   implements RowMapper<Blog>{

    @Override
    public Blog mapRow(ResultSet rs) throws SQLException {
        Blog blog = new Blog();
        blog.setId(rs.getLong("id"));
        blog.setTitle(rs.getString("title"));
        blog.setContent(rs.getString("content"));
        blog.setCreatedDate(rs.getTimestamp("created_date").toLocalDateTime());
        blog.setShopId(rs.getLong("shop_id"));
        return blog;
    }
}
