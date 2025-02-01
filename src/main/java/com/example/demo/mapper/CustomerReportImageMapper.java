package com.example.demo.mapper;

import com.example.demo.entity.customer_report_image;
import com.example.demo.entity.customer_report;


import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerReportImageMapper implements RowMapper<customer_report_image> {

    @Override
    public customer_report_image mapRow(ResultSet rs) {
        customer_report_image customerReportImage = new customer_report_image();

        try {
            customerReportImage.setReportImageId(rs.getInt("report_image_id"));
            customerReportImage.setReportImageContent(rs.getString("report_image_content"));

            customer_report customerReport = new customer_report();
            customerReport.setReportId(rs.getInt("report_id"));
            customerReportImage.setCustomerReport(customerReport);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customerReportImage;
    }
}
