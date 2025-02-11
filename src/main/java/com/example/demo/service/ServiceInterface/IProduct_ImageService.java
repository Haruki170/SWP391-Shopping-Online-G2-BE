package com.example.demo.service.ServiceInterface;

import com.example.demo.entity.ProductImage;

import java.util.List;

public interface IProduct_ImageService {
    public List<ProductImage> getProductImagesByProductID(int productID);
}
