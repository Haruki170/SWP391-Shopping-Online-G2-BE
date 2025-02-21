package com.example.demo.repository;
import com.example.demo.connect.DBContext;
import com.example.demo.entity.customer_report_type;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@Repository
public class ReportTypeRepository {
    public List<customer_report_type> findAll() {
        List<customer_report_type> types = new ArrayList<>();
        String sql = "SELECT * FROM customer_report_type";

        try (Connection connection = DBContext.getConnect();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                customer_report_type type = new customer_report_type();
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
