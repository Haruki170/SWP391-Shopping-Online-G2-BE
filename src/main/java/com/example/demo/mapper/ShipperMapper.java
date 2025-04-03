package com.example.demo.mapper;

import com.example.demo.entity.Shipper;
import java.sql.ResultSet;

public class ShipperMapper implements RowMapper<Shipper> {
    @Override
    public Shipper mapRow(ResultSet rs) {
        Shipper shipper = new Shipper();
        try {
            shipper.setId(rs.getInt("shipper_id"));
            shipper.setName(rs.getString("shipper_name"));
            shipper.setEmail(rs.getString("shipper_email"));
            shipper.setPhone(rs.getString("shipper_phone"));
            shipper.setAddress(rs.getString("shipper_address"));
            shipper.setStatus(rs.getInt("shipper_status"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return shipper;
    }
}