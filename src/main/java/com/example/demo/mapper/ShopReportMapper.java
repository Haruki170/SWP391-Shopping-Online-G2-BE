package com.example.demo.mapper;

import com.example.demo.entity.ShopReport;
import com.example.demo.entity.shop_report;

import java.sql.ResultSet;

public class ShopReportMapper implements RowMapper<shop_report> {

    @Override
    public shop_report mapRow(ResultSet rs) {
        shop_report shop_report = new shop_report();
        try {
            shop_report.setId(rs.getInt("id"));
            shop_report.setReportResponse(rs.getString("report_response"));
            shop_report.setReportContent(rs.getString("report_content"));
            shop_report.setCreate_at(rs.getString("create_at"));
            shop_report.setUpdate_at(rs.getString("update_at"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return shop_report;
    }
}
