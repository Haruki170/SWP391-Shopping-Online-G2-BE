package com.example.demo.mapper;

import com.example.demo.entity.OrderCancelImage;

import java.sql.ResultSet;

public class OrderCancelImageMapper implements RowMapper<OrderCancelImage> {

    @Override
    public OrderCancelImage mapRow(ResultSet rs) {
        OrderCancelImage orderCancelImage = new OrderCancelImage();
        try {
            orderCancelImage.setId(rs.getInt("id"));
            orderCancelImage.setImage(rs.getString("image"));
        }catch (Exception e) {
            e.printStackTrace();
        }
        return orderCancelImage;
    }
}


