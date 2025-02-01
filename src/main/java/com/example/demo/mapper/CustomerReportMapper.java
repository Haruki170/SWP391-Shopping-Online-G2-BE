package com.example.demo.mapper;

import com.example.demo.entity.ReportType;
import com.example.demo.entity.customer_report;
import com.example.demo.entity.Customer;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerReportMapper implements RowMapper<customer_report> {

    @Override
    public customer_report mapRow(ResultSet rs)  {
        customer_report customerReport = new customer_report();

        try {
            customerReport.setReportId(rs.getInt("report_id"));
            customerReport.setReportContent(rs.getString("report_content"));
            customerReport.setReportResponse(rs.getString("report_response"));
            customerReport.setCustomerId(rs.getInt("customer_id"));


            ReportType loaiPhanHoi = new ReportType();

            customerReport.setLoaiPhanHoi(loaiPhanHoi);

            int typeId = rs.getInt("type_id");
            ReportType type = new ReportType();
            type.setId(typeId);
            customerReport.setType(type);
        } catch (SQLException e) {
            e.printStackTrace();

        }

        return customerReport;
    }
}