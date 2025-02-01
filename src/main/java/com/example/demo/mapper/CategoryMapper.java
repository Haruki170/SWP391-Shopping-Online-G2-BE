package com.example.demo.mapper;

import com.example.demo.entity.Category;

import java.sql.ResultSet;

public class CategoryMapper   implements  RowMapper<Category>{

    @Override
    public Category mapRow(ResultSet rs) {
        try {
            Category category = new Category();
            category.setId(rs.getInt("category_id"));
            category.setName(rs.getString("category_name"));
            category.setStatus(rs.getInt("status"));
            category.setParent(rs.getInt("parent"));
            return category;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
