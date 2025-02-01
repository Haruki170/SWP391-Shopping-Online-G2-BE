package com.example.demo.mapper;

import com.example.demo.entity.OrderTransaction;

import java.sql.ResultSet;

public class OrderTransactionMapper implements RowMapper<OrderTransaction> {
    @Override
    public OrderTransaction mapRow(ResultSet rs) {
        OrderTransaction ot = new OrderTransaction();
        try {
            ot.setId(rs.getInt("id"));
            ot.setValue(rs.getString("value"));
        }catch (Exception e) {
            e.printStackTrace();
        }
        return ot;
    }
}
