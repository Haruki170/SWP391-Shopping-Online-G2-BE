package com.example.demo.mapper;

import com.example.demo.entity.ShipCost;

import java.sql.ResultSet;

public class ShipCostMapper implements RowMapper<ShipCost> {
    @Override
    public ShipCost mapRow(ResultSet rs) {
        ShipCost shipCost = new ShipCost();
        try {
            shipCost.setId(rs.getInt("shipping_cost_id"));
            shipCost.setStartWeight(rs.getDouble("start_calculated_weight"));
            shipCost.setEndWeight(rs.getDouble("end_calculated_weight"));
            shipCost.setCost(rs.getInt("cost"));
        }catch (Exception e) {
            e.printStackTrace();
        }
        return shipCost;
    }
}
