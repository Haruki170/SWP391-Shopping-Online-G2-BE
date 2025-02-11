package com.example.demo.service.ServiceInterface;

import com.example.demo.entity.ProductAddOn;

import java.util.List;

public interface IProductAddOnService {

    ProductAddOn addAddOn(ProductAddOn productAddOn);
    void updateProductAddOn(ProductAddOn productAddOn);

    void deleteProductAddOn(int productAddOn);
}
