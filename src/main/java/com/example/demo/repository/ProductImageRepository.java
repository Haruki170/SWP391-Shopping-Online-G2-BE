package com.example.demo.repository;

import com.example.demo.entity.ProductImage;
import com.example.demo.mapper.ProductImageMapper;
import com.example.demo.repository.repositoryInterface.IProductImageRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository
public class ProductImageRepository extends AbstractRepository<ProductImage> implements IProductImageRepository {
    @Override
    public List<ProductImage> findProductImageById(long id) {
        List<ProductImage> productImages = new ArrayList<>();
        String sql = "select * FROM product_image WHERE product_id = ?";
        productImages = this.findAll(sql, new ProductImageMapper(), id);
        return productImages;
    }
    @Override
    public ProductImage addProductImageById(ProductImage productImage) {
        String sql = "INSERT INTO product_image( product_id, product_image) VALUES (?,?)";
        System.out.println("add Product: "+productImage.getProductId());
        super.save(sql, productImage.getProductId(), productImage.getImage());
        return productImage;
    }
    @Override
    public void  removeProductImageById(int imageId) {
        String sql = "DELETE FROM product_image WHERE product_image_id = ?";
        super.save(sql, imageId);
    }
    @Override
    public void updateProductImage(int imageId, String newImageUrl) {
        String sql = "UPDATE product_image SET product_image = ? WHERE  product_image_id = ?";
        super.save(sql, newImageUrl, imageId);

    }
    @Override
    public void updateAllProductImages(int productId, List<String> imageUrls) {
        String sql = "INSERT INTO product_image(product_id, product_image) VALUES (?, ?)";
        for (String imageUrl : imageUrls) {
            super.save(sql,  productId, imageUrl);
        }
    }


}
