package com.example.demo.mapper;

import com.example.demo.response.CategoryResponse;

import java.sql.ResultSet;

public class CategoryResponseMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet rs) {
        CategoryResponse categoryResponse = new CategoryResponse();
        try {
            categoryResponse.setLabel(rs.getString("category_name"));
            categoryResponse.setValue(rs.getInt("category_id"));

        }catch (Exception e) {
            e.printStackTrace();
        }
        return categoryResponse;
    }
}
