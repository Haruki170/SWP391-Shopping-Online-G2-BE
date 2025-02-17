package com.example.demo.repository;

import com.example.demo.connect.DBContext;
import com.example.demo.entity.ShopReportType;
import com.example.demo.entity.customer_report_type;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ShopReportTypeRespository {
    public List<ShopReportType> findAll() {
        List<ShopReportType> types = new ArrayList<>();
        String sql = "SELECT * FROM shop_report_type";

        try (Connection connection = DBContext.getConnect();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                ShopReportType type = new ShopReportType();
                type.setId(resultSet.getInt("id"));
                type.setType(resultSet.getString("type"));
                types.add(type);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return types;
    }
}
