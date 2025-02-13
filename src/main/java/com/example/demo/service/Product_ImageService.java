package com.example.demo.service;

import com.example.demo.entity.ProductImage;
import com.example.demo.repository.ProductImageRepository;
import com.example.demo.service.ServiceInterface.IProduct_ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class Product_ImageService  implements IProduct_ImageService {
   @Autowired
    private  ProductImageRepository productImageRepository;

    @Override
    public List<ProductImage> getProductImagesByProductID(int productID) {
        return productImageRepository.findProductImageById(productID);
    }
}
