package com.example.demo.repository;

import com.example.demo.entity.ProductOption;
import com.example.demo.mapper.ProductOptionMapper;
import com.example.demo.repository.repositoryInterface.IProductOptionRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository
public class ProductOptionRepository extends AbstractRepository<ProductOption> implements IProductOptionRepository {

    @Override
    public List<ProductOption> findProductOptionById(long id) {
        List<ProductOption> options = new ArrayList<>();
        String sql = "select * FROM product_option WHERE product_id = ?";
        options = this.findAll(sql, new ProductOptionMapper(), id);
        return options;
    }

    @Override
    public List<ProductOption> getAll() {
        return null;
    }

    @Override
    public ProductOption insertProductOption(ProductOption productOption, int productId) {
        String sql = "INSERT INTO product_option (product_option_name, quantity, product_id) VALUES (?, ?, ?)";
        super.save(sql, productOption.getName(),productOption.getQuantity(), productId);

        return productOption;
    }
    @Override
    public ProductOption updateProductOption(ProductOption productOption) {
        // SQL query to update the existing product_option record
        String sql = "UPDATE product_option SET product_option_name = ?, quantity = ? WHERE product_option_id = ?";

        // Execute the update query
        super.save(sql, productOption.getName(), productOption.getQuantity(), productOption.getId());

        return productOption; // Return the updated ProductOption
    }
    @Override
    public void deleteProductOption(int id) {
        String sql = "DELETE FROM `product_option` WHERE `product_option_id` = ?";
        super.save(sql, id);
    }

    @Override
    // New method to delete options by product ID
    public ProductOption deleteOptionsByProductId(int productId) {
        String sql = "DELETE FROM product_option WHERE product_id = ?";
        super.delete(sql, productId);
        return null;
    }

}

