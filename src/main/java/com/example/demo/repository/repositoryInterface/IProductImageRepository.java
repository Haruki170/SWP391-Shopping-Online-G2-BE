package com.example.demo.repository.repositoryInterface;

import com.example.demo.entity.ProductImage;

import java.util.List;

public interface IProductImageRepository {
    List<ProductImage> findProductImageById(long id);

    ProductImage addProductImageById(ProductImage productImage);

    void  removeProductImageById(int imageId);

    void updateProductImage(int imageId, String newImageUrl);

    void updateAllProductImages(int productId, List<String> imageUrls);
}
