package com.example.demo.repository.repositoryInterface;

import com.example.demo.entity.ProductImage;

import java.util.List;

public interface IProduct_imageRespository {
    public List<ProductImage> getProductImagesByProductID(int productID);
}
