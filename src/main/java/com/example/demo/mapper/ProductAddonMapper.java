package com.example.demo.mapper;

import com.example.demo.entity.ProductAddOn;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class ProductAddonMapper implements RowMapper<ProductAddOn> {
    @Override
    public ProductAddOn mapRow(ResultSet rs) {
        ProductAddOn productAddOn = new ProductAddOn();
        try {
            // Kiểm tra xem cột "product_addon_id" có tồn tại trong ResultSet không
            if (hasColumn(rs, "product_addon_id")) {
                int id = rs.getInt("product_addon_id");
                if (!rs.wasNull()) {
                    productAddOn.setId(id);
                }
            }

            // Thiết lập các giá trị khác
            productAddOn.setName(rs.getString("product_addon_name"));
            productAddOn.setPrice(rs.getInt("product_addon_price"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productAddOn;
    }

    // Phương thức kiểm tra xem một cột có tồn tại trong ResultSet không
    private boolean hasColumn(ResultSet rs, String columnName) {
        try {
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                if (metaData.getColumnName(i).equalsIgnoreCase(columnName)) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
