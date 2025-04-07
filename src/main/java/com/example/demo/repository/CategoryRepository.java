package com.example.demo.repository;

import com.example.demo.dto.ProductCategoryDto;
import com.example.demo.entity.Category;
import com.example.demo.connect.DBContext;
import com.example.demo.entity.Product;
import com.example.demo.mapper.CategoryMapper;
import com.example.demo.mapper.CategoryResponseMapper;
import com.example.demo.mapper.ProductMapper;
import com.example.demo.repository.repositoryInterface.ICategoryRepository;
import com.example.demo.response.CategoryResponse;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CategoryRepository extends AbstractRepository<Category>  implements ICategoryRepository {
    // Tạo mới một Category
    @Override
    public Category createCategory(Category category) {
        String sql = "INSERT INTO category (category_name, status, parent) VALUES (?, ?, ?)";
        try (Connection connection = DBContext.getConnect();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, category.getName());
            statement.setInt(2, category.getStatus());
            statement.setInt(3, category.getParent());

            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                category.setId(generatedKeys.getInt(1));
            }

            return category;
        } catch (SQLException ex) {
            ex.printStackTrace();

            return null;
        }
    }
    public List<Category> searchCategories(String name, Integer status, Integer parent) {
        StringBuilder sql = new StringBuilder("SELECT * FROM category WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (name != null && !name.isEmpty()) {
            sql.append(" AND category_name LIKE ?");
            params.add("%" + name + "%");
        }
        if (status != null) {
            sql.append(" AND status = ?");
            params.add(status);
        }
        if (parent != null) {
            sql.append(" AND parent = ?");
            params.add(parent);
        }

        return super.findAll(sql.toString(), new CategoryMapper(), params.toArray());
    }

    public List<CategoryResponse> getAllCategory() {
        String sql = "Select * from `category` where status=1";
        return super.findAll(sql, new CategoryResponseMapper());
    }
    public List<CategoryResponse> getAllCategoryByProductId(int productId) {
        String sql = "SELECT c.* FROM `product_category` pc join category c on pc.category_id = c.category_id where product_id = ? ;";
        return super.findAll(sql, new CategoryResponseMapper(), productId);
    }
    public boolean existsByCategoryName(String name) {
        String sql = "SELECT COUNT(*) FROM category WHERE category_name = ?";

        try (Connection connection = DBContext.getConnect();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, name);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return false;
    }

    public boolean existsById(Integer id) {
        String sql = "SELECT COUNT(*) FROM category WHERE category_id = ?";

        try (Connection connection = DBContext.getConnect();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return false;
    }

    @Override
    public List<Category> getAllCategories() {
        String sql = "SELECT * FROM `category` WHERE (category.parent IS NULL OR category.parent = 0)";
        return super.findAll(sql, new CategoryMapper());
    }



    //Lấy category theo ID
    @Override
    public Category getCategoryById(int id) {
        String sql = "SELECT * FROM `category` WHERE category_id = ?";

        try (Connection connection = DBContext.getConnect();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    Category category = new Category();
                    category.setId(rs.getInt("category_id"));
                    category.setName(rs.getString("category_name"));
                    category.setStatus(rs.getInt("status"));
                    category.setParent(rs.getInt("parent"));
                    category.setSubcategories(getSubcategoriesByParentId(category.getId()));

                    return category;
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    //  lấy danh sách subcategories theo parentId
    public List<Category> getSubcategoriesByParentId(int parentId) {
        String sql = "SELECT * FROM category WHERE parent = ?";
        return super.findAll(sql,new CategoryMapper(), parentId);

    }


    @Override
    public Category updateCategory(Category category) {
        String sql = "UPDATE category SET category_name = ?, status = ?, parent = ? WHERE category_id = ?";

        try (Connection connection = DBContext.getConnect();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, category.getName());
            statement.setInt(2, category.getStatus());
            statement.setObject(3, category.getParent() != 0 ? category.getParent() : null, java.sql.Types.INTEGER);
            statement.setInt(4, category.getId());

            statement.executeUpdate();
            category.setSubcategories(getSubcategoriesByParentId(category.getId()));

            return category;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    //Xóa Category
    @Override
    public void deleteCategory(int id) {
        String sql = "DELETE FROM `category` WHERE category_id = ?";

        try (Connection connection = DBContext.getConnect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public List<ProductCategoryDto> getProductsByCategoryId(int categoryId) {
        System.out.println("cate: " +categoryId);
        String sql = "SELECT p.product_id, p.product_name, p.product_price, p.product_avatar " +
                "FROM product p " +
                "INNER JOIN product_category pc ON p.product_id = pc.product_id " +
                "WHERE pc.category_id = ? and p.status = 1";
        List<ProductCategoryDto> products = new ArrayList<>();

        try (Connection connection = DBContext.getConnect();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, categoryId);

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    ProductCategoryDto productDto = new ProductCategoryDto();
                    productDto.setId(rs.getInt("product_id"));
                    productDto.setName(rs.getString("product_name"));
                    productDto.setPrice(rs.getInt("product_price"));
                    productDto.setAvatar(rs.getString("product_avatar"));
                    products.add(productDto);
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        System.out.println(products);
        return products;
    }

    public List<Product> getProductsByCategory(int categoryId) {
        AbstractRepository<Product> a = new AbstractRepository();
        String sql = "SELECT p.product_id, p.product_name, p.product_price, p.product_avatar " +
                "FROM product p " +
                "INNER JOIN product_category pc ON p.product_id = pc.product_id " +
                "WHERE pc.category_id = ?";
        return a.findAll(sql, new ProductMapper(), categoryId);
    }

    @Override
    public int checkCategoryStatus(int categoryId) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DBContext.getConnect();
            String sql = "SELECT status FROM category WHERE category_id = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, categoryId);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                int status = rs.getInt("status");
                return status;
            } else {
                throw new SQLException("Category not found with ID: " + categoryId);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return -1;
    }
    @Override
    public List<Category> getCategoryByProduct(int id){
        String sql = "SELECT c.*, pc.product_id FROM category AS c\n" +
                "JOIN product_category AS pc\n" +
                "ON c.category_id = pc.category_id\n" +
                "WHERE product_id = ?";
        return super.findAll(sql, new CategoryMapper(), id);
    }

    public List<Category> getCategoryParent(){
        String sql = "select * from category where parent=0";
        return super.findAll(sql, new CategoryMapper());
    }


}

