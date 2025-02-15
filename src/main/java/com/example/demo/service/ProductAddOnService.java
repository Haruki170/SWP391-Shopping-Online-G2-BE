package com.example.demo.service;

import com.example.demo.entity.ProductAddOn;
import com.example.demo.repository.repositoryInterface.IProductAddonRepository;
import com.example.demo.service.ServiceInterface.IProductAddOnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductAddOnService implements IProductAddOnService {
    @Autowired
    IProductAddonRepository productAddonRepository;

    @Override
    public ProductAddOn addAddOn(ProductAddOn productAddOn) {
        return productAddonRepository.insertProductAddon(productAddOn,productAddOn.getProductId());
    }
    @Override
    public void updateProductAddOn(ProductAddOn productAddOn) {
        productAddonRepository.updateProductAddon(productAddOn);
    }
    @Override
    public void deleteProductAddOn(int productAddOn) {
        productAddonRepository.deleteProductAddon(productAddOn);
    }
}
