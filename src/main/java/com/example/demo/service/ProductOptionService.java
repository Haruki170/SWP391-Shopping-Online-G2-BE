package com.example.demo.service;

import com.example.demo.entity.ProductOption;
import com.example.demo.repository.ProductOptionRepository;
import com.example.demo.service.ServiceInterface.IProductOptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductOptionService implements IProductOptionService {

    @Autowired
    private ProductOptionRepository productOptionRepository;


    @Override
    public ProductOption addOption(ProductOption productOption) {
        return productOptionRepository.insertProductOption(productOption,productOption.getProductId());
    }
    @Override
    public ProductOption updateOption(ProductOption productOption) {
        return productOptionRepository.updateProductOption(productOption);
    }
    @Override
    public void updateOption(int id) {
         productOptionRepository.deleteProductOption(id);
    }
}
