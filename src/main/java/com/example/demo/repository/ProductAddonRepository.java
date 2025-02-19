package com.example.demo.repository;

import com.example.demo.entity.ProductAddOn;
import com.example.demo.mapper.ProductAddonMapper;
import com.example.demo.repository.repositoryInterface.IProductAddonRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository
public class ProductAddonRepository extends AbstractRepository<ProductAddOn> implements IProductAddonRepository {

    @Override
    public List<ProductAddOn> findProductAddonById(long id) {
        List<ProductAddOn> productAddOns = new ArrayList<>();
        String sql = "select * FROM product_addon WHERE product_id = ?";
        productAddOns = this.findAll(sql, new ProductAddonMapper(), id);
        return productAddOns;
    }





    @Override
    public ProductAddOn insertProductAddon(ProductAddOn productAddOn, int productid) {
        String sql = "INSERT INTO product_addon (product_addon_name, product_addon_price, product_id) VALUES ( ?, ?, ?)";
        super.save(sql, productAddOn.getName(), productAddOn.getPrice(), productid);
        return productAddOn;
    }
    @Override
    public ProductAddOn updateProductAddon(ProductAddOn productAddOn) {
        // Câu lệnh SQL cập nhật chỉ tên và giá
        String sql = "UPDATE product_addon SET product_addon_name = ?, product_addon_price = ? WHERE product_addon_id = ?";

        // Gọi phương thức save (hoặc update) để thực hiện cập nhật vào cơ sở dữ liệu
        super.save(sql, productAddOn.getName(), productAddOn.getPrice(),productAddOn.getId());

        // Trả về đối tượng ProductAddOn đã cập nhật
        return productAddOn;
    }
    @Override
    public void deleteProductAddon(int id) {
        String sql = "delete from product_addon WHERE product_addon_id = ?";
        super.save(sql, id);
    }

    @Override
    public List<ProductAddOn> findAllProductAddonByID(String ids) {
        // Tách chuỗi IDs thành mảng
        String[] idArray = ids.split(",");
        // Tạo câu lệnh SQL với điều kiện IN
        String sql = "SELECT * FROM product_addon WHERE product_addon_id IN (";
        // Tạo tham số cho câu lệnh SQL
        StringBuilder placeholders = new StringBuilder();
        for (int i = 0; i < idArray.length; i++) {
            placeholders.append("?");
            if (i < idArray.length - 1) {
                placeholders.append(", ");
            }
        }
        sql += placeholders + ")";

        // Truyền tham số vào phương thức findAll
        return findAll(sql, new ProductAddonMapper(), idArray);
    }

    @Override
    // New method to delete addons by product ID
    public ProductAddOn deleteAddOnsByProductId(int productId) {
        String sql = "DELETE FROM product_option WHERE product_id = ?";
        super.delete(sql, productId);
        return null;
    }


}

