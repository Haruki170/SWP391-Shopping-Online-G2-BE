package com.example.demo.repository;

import com.example.demo.connect.DBContext;
import com.example.demo.entity.*;
import com.example.demo.mapper.ProductAddonMapper;
import com.example.demo.mapper.ProductImageMapper;
import com.example.demo.mapper.ProductMapper;
import com.example.demo.mapper.ProductOptionMapper;
import com.example.demo.repository.repositoryInterface.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProductRepository extends AbstractRepository<Product> implements IProductRepository {

    @Override
    public Product findById(int id) {
        String sql ="select * FROM product as p JOIN shop as s on p.shop_id =s.shop_id where product_id = ? and p.status = 1";
        Product product = super.findOne(sql,new ProductMapper(), id);
        System.out.println(id);
        System.out.println("dẫ tìm");
        return product;
    }

    @Override
    public Product findByIdAll(int id) {
        String sql ="select * FROM product as p JOIN shop as s on p.shop_id =s.shop_id where product_id = ? ";
        Product product = super.findOne(sql,new ProductMapper(), id);
        System.out.println(id);
        System.out.println("dẫ tìm");
        return product;
    }

    @Override
    public List<Product> findProductsById(int id) {
        String sql = "SELECT p.*, s.*, c.category_id, c.category_name " +
                "FROM product AS p " +
                "JOIN shop AS s ON p.shop_id = s.shop_id " +
                "LEFT JOIN product_category AS pc ON p.product_id = pc.product_id " +
                "LEFT JOIN category AS c ON pc.category_id = c.category_id " +
                "WHERE p.product_id = ? and p.status = 1";
        return super.findAll(sql, new ProductMapper(), id);
    }


    public Product findById2(int id) {
        String sql ="select * FROM product as p JOIN shop as s on p.shop_id =s.shop_id where product_id = ?";
        Product product = super.findOne(sql,new ProductMapper(), id);
        System.out.println("dẫ tìm");
        return product;
    }


    @Override
    public List<Product> findAll(int shopid) {
        String sql ="select * FROM product as p JOIN shop as s on p.shop_id =s.shop_id  where s.shop_id = ? ";
        return super.findAll(sql,new ProductMapper(),shopid);
    }

    public int findShopIDbyProductID(int productID) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DBContext.getConnect();
            statement = connection.createStatement();
            String sql = "SELECT product.shop_id FROM `product` WHERE product_id = '"+productID+"'";
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                return resultSet.getInt(1);
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
        return -1;
    }

    @Override
    public boolean insert(Product product ,int shopid) {
        System.out.println(shopid);
        String sql = "INSERT INTO `product` (product_name,product_avatar, product_desc, product_price,status, shop_id, weight, length, width , height ) VALUES (?, ?,?,?,0,?,?,?,?,?)";
        boolean isInserted = super.save(sql, product.getName(),product.getAvatar(), product.getDescription(),
                product.getPrice(), shopid, product.getWeight(),product.getLength(),product.getWidth(),
                product.getHeight());
        if(isInserted){
            // Retrieve the last inserted product ID
            int productId = getLastId();

            // Now insert into product_category
            String insertCategorySQL = "INSERT INTO product_category (product_id, category_id) VALUES (?, ?)";
            for (Category category : product.getCategories()) {
                super.save(insertCategorySQL, productId, category.getId());
            }
        }

        return isInserted;
    }
    @Override
    public int getLastId (){
        String sql = "Select * from product order by product_id desc limit 1";
        Product product = super.findOne(sql,new ProductMapper());
        return product.getId();
    }




    @Override
    public Product update(Product product) {
        String sql = "UPDATE `product` SET product_name=?, product_avatar=?, product_desc=?, product_price=?, weight=?, length=?, width=?, height=? WHERE product_id=?";

        try (Connection connection = DBContext.getConnect();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            // Set the parameters for the prepared statement
            statement.setString(1, product.getName());
            statement.setString(2, product.getAvatar());
            statement.setString(3, product.getDescription());
            statement.setInt(4, product.getPrice());
            statement.setDouble(5, product.getWeight());
            statement.setDouble(6, product.getLength());
            statement.setDouble(7, product.getWidth());
            statement.setDouble(8, product.getHeight());
            statement.setInt(9, product.getId());

            // Execute the update
            int rowsAffected = statement.executeUpdate();

            // Check if the update was successful
            if (rowsAffected > 0) {
                return product; // Return the updated product
            }
        } catch (SQLException ex) {
            // Consider using a logging framework instead of printStackTrace
            ex.printStackTrace();
        }
        return null; // Return null if the update failed
    }


    @Override
    public void delete(Product product) {
        Connection connection = null;
        Statement statement = null;

        try {
            connection = DBContext.getConnect();
            connection.setAutoCommit(false); // Start transaction

            statement = connection.createStatement();

            // Delete related product images
            String deleteImagesSQL = String.format("DELETE FROM `product_image` WHERE `product_id` = %d", product.getId());
            statement.executeUpdate(deleteImagesSQL);

            // Delete related product categories
            String deleteCategoriesSQL = String.format("DELETE FROM `product_category` WHERE `product_id` = %d", product.getId());
            statement.executeUpdate(deleteCategoriesSQL);

            // Delete related product addons
            String deleteAddonsSQL = String.format("DELETE FROM `product_addon` WHERE `product_id` = %d", product.getId());
            statement.executeUpdate(deleteAddonsSQL);

            // Delete related product options
            String deleteOptionsSQL = String.format("DELETE FROM `product_option` WHERE `product_id` = %d", product.getId());
            statement.executeUpdate(deleteOptionsSQL);

            // Finally, delete the product itself
            String deleteProductSQL = String.format("DELETE FROM `product` WHERE `product_id` = %d", product.getId());
            statement.executeUpdate(deleteProductSQL);

            // Commit the transaction
            connection.commit();

        } catch (SQLException ex) {
            // If an exception occurs, roll back the transaction
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            ex.printStackTrace();
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    public List<Product> findProductByCategoryHot(int cID) {
        List<Product> products = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DBContext.getConnect();
            String sql = "WITH r AS ( " +
                    "    SELECT p.* " +
                    "    FROM product_category AS pc " +
                    "    JOIN product AS p ON pc.product_id = p.product_id " +
                    "    JOIN category AS c ON c.category_id = pc.category_id " +
                    "    WHERE c.category_id = ? " +
                    "), " +
                    "p AS ( " +
                    "    SELECT p.* " +
                    "    FROM product_category AS pc " +
                    "    JOIN product AS p ON pc.product_id = p.product_id " +
                    "    JOIN category AS c ON c.category_id = pc.category_id " +
                    "    WHERE c.category_name LIKE '%hot%' " +
                    ") " +
                    "SELECT * FROM r WHERE r.product_id IN (SELECT product_id FROM p)";

            statement = connection.prepareStatement(sql);
            statement.setInt(1, cID);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getInt("product_id"));
                product.setName(resultSet.getString("product_name"));
                product.setPrice((int) resultSet.getDouble("product_price"));
                product.setAvatar(resultSet.getString("product_avatar"));
                product.setDescription(resultSet.getString("product_desc"));
                products.add(product);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return products;

    }


    @Override
    public Product updateStatus(Product product) {
        String sql = "UPDATE `product` SET status = ? WHERE product_id=?";

        try (Connection connection = DBContext.getConnect();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, product.getStatus());
            statement.setInt(2, product.getId());

            // Execute the update
            int rowsAffected = statement.executeUpdate();

            // Check if the update was successful
            if (rowsAffected > 0) {
                return product; // Return the updated product
            }
        } catch (SQLException ex) {
            // Consider using a logging framework instead of printStackTrace
            ex.printStackTrace();
        }
        return null; // Return null if the update failed
    }


    @Override
    public List<Map<String, Object>> findByNameContaining(String searchTerm) {
        List<Map<String, Object>> results = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DBContext.getConnect();

            // SQL query to search products by name or shop by name, including shop_logo
            String sql = "SELECT 'product' AS type, p.product_id AS id, p.product_name AS name, "
                    + "p.product_price AS price, p.product_avatar AS avatar, p.product_desc AS description, "
                    + "s.shop_id AS shopId, s.shop_name AS shopName, s.shop_logo AS shopLogo, p.status AS status "
                    + "FROM product p "
                    + "LEFT JOIN shop s ON p.shop_id = s.shop_id "
                    + "WHERE p.product_name LIKE ? AND p.status = 1 AND s.status = 2 "
                    + "UNION "
                    + "SELECT 'shop' AS type, s.shop_id AS id, s.shop_name AS name, NULL AS price, "
                    + "NULL AS avatar, NULL AS description, s.shop_id AS shopId, s.shop_name AS shopName, "
                    + "s.shop_logo AS shopLogo, NULL AS status "
                    + "FROM shop s "
                    + "WHERE s.shop_name LIKE ? AND s.status = 2";

            statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + searchTerm + "%");
            statement.setString(2, "%" + searchTerm + "%");

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Map<String, Object> result = new HashMap<>();
                result.put("type", resultSet.getString("type"));
                result.put("id", resultSet.getInt("id"));
                result.put("name", resultSet.getString("name"));
                result.put("price", resultSet.getObject("price"));
                result.put("avatar", resultSet.getString("avatar"));
                result.put("description", resultSet.getString("description"));
                result.put("shopId", resultSet.getObject("shopId"));
                result.put("shopName", resultSet.getString("shopName"));
                result.put("logo", resultSet.getString("shopLogo"));
                result.put("status", resultSet.getString("status"));
                results.add(result);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return results;
    }



    @Override
    public List<Product> findAllProductByShopAdmin(int shopId) {
        String sql = "SELECT * FROM product WHERE shop_id = ?";
        return super.findAll(sql,new ProductMapper(),shopId);
    }

    @Override
    public boolean updateStatus(int id, int status) {
        String sql = "Update product set status = ? where product_id = ?";
        return super.save(sql,status,id);
    }
    @Override
    public Product FindCategoryByProductID(int productId) {
        String sql = "SELECT c.* FROM category AS c " +
                "JOIN product_category AS pc ON c.category_id = pc.category_id " +
                "WHERE pc.product_id = ?";
        return super.findOne(sql, new ProductMapper(), productId);
    }

    @Override
    public List<Product> findProductsByProvince(String province) {
        return List.of();
    }


    @Override
    public Product FindOAByID(int id) {
        String sql = "SELECT * FROM product AS p " +
                "JOIN shop AS s ON p.shop_id = s.shop_id " +
                "LEFT JOIN product_option AS po ON p.product_id = po.product_id " +
                "LEFT JOIN product_addon AS pa ON p.product_id = pa.product_id " +
                "WHERE p.product_id = ? and p.status = 1";
        Product product = super.findOne(sql, new ProductMapper(), id);
        return product;
    }







    public List<Product> findByFilters(int category, String province, int minRating, double minPrice) {
        List<Product> products = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT p.*, AVG(f.feedback_rating) AS avg_rating " +
                        "FROM product AS p " +
                        "JOIN product_category AS pc ON p.product_id = pc.product_id " +
                        "JOIN shop_address AS sa ON p.shop_id = sa.shop_id " +
                        "LEFT JOIN customer_feedback AS f ON f.product_id = p.product_id " +
                        "WHERE pc.category_id = ? " +
                        "AND p.product_price >= ? " +
                        "AND p.status = 1 "  // Thêm điều kiện sản phẩm có status = 1
        );

        if (!province.isEmpty()) {
            sql.append("AND sa.shop_address_province = ? ");
        }

        sql.append("GROUP BY p.product_id HAVING avg_rating >= ?");

        try (Connection connection = DBContext.getConnect();
             PreparedStatement statement = connection.prepareStatement(sql.toString())) {

            statement.setInt(1, category);
            statement.setDouble(2, minPrice);
            int parameterIndex = 3;

            if (!province.isEmpty()) {
                statement.setString(parameterIndex++, province);
            }

            statement.setInt(parameterIndex, minRating);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Product product = new Product();
                    product.setId(resultSet.getInt("product_id"));
                    product.setName(resultSet.getString("product_name"));
                    product.setPrice(resultSet.getInt("product_price"));
                    product.setAvatar(resultSet.getString("product_avatar"));
                    product.setDescription(resultSet.getString("product_desc"));
                    product.setRating(resultSet.getDouble("avg_rating"));
                    products.add(product);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return products;
    }



    @Override
    public List<Product> findByCart(int id) { // duy anh
        return List.of();
    }

    @Override
    public List<Product> findByWishList(int id) {//dung
        return List.of();
    }

    public List<Product> findProductSaleByCate(int cateId) {
        String sql = "SELECT \n" +
                "    c.category_id,\n" +
                "    c.category_name,\n" +
                "    p.*,\n" +
                "    COALESCE(SUM(od.quantity), 0) AS total_quantity_sold\n" +
                "FROM \n" +
                "    category c\n" +
                "JOIN \n" +
                "    product_category pc ON c.category_id = pc.category_id\n" +
                "JOIN \n" +
                "    product p ON pc.product_id = p.product_id\n" +
                "JOIN \n" +
                "    shop s ON p.shop_id = s.shop_id\n" +  // Thêm JOIN với bảng shop
                "LEFT JOIN \n" +
                "    order_detail od ON p.product_id = od.product_id\n" +
                "WHERE \n" +
                "    c.category_id = ? \n" +
                "    AND p.status = 1 \n" +  // Điều kiện sản phẩm có status = 1
                "    AND s.status = 2  -- Điều kiện shop có status = 2\n" +
                "GROUP BY \n" +
                "    p.product_id\n" +
                "ORDER BY \n" +
                "    total_quantity_sold DESC\n" +
                "LIMIT 4;";
        return super.findAll(sql, new ProductMapper(), cateId);
    }

}

