package com.example.demo.repository;

import com.example.demo.connect.DBContext;
import com.example.demo.entity.ProductImage;
import com.example.demo.repository.repositoryInterface.IProduct_imageRespository;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
@Repository
public class Product_imageRespository extends AbstractRepository<ProductImage> implements IProduct_imageRespository {
    @Override
    public List<ProductImage> getProductImagesByProductID(int productID) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<ProductImage> productImages = new ArrayList<>();

        try {
            connection = DBContext.getConnect();
            statement = connection.createStatement();
            String sql = "SELECT product_image_id, product_image, create_at, update_at FROM product_image WHERE product_id = " + productID;
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                ProductImage productImage = new ProductImage();
                productImage.setId(resultSet.getInt("product_image_id"));
                productImage.setImage(resultSet.getString("product_image"));
                productImages.add(productImage);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return productImages;
    }

}
