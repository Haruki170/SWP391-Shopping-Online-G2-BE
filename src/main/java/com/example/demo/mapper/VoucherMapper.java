package com.example.demo.mapper;

import com.example.demo.entity.Voucher;

import java.sql.ResultSet;

public class VoucherMapper implements RowMapper<Voucher> {
    @Override
    public Voucher mapRow(ResultSet rs) {
        Voucher voucher = new Voucher();
        try {
            voucher.setId(rs.getInt("voucher_id"));
            voucher.setCode(rs.getString("code"));
            voucher.setDescription(rs.getString("description"));
            voucher.setDiscountAmount(rs.getInt("discount_amount"));
            voucher.setMinOrderAmount(rs.getInt("min_order_amount"));
            voucher.setStartDate(rs.getString("start_date"));
            voucher.setEndDate(rs.getString("end_date"));
            voucher.setQuantity(rs.getInt("quantity"));

            voucher.setCreate_at(rs.getString("created_at"));
            voucher.setUpdate_at(rs.getString("updated_at"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return voucher;
        }
}
