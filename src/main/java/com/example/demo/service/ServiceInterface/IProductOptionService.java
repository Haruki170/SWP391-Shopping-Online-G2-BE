package com.example.demo.service.ServiceInterface;


import com.example.demo.entity.ProductOption;

public interface IProductOptionService {

    ProductOption addOption(ProductOption productOption);

    ProductOption updateOption(ProductOption productOption);

    void updateOption(int id);
}
