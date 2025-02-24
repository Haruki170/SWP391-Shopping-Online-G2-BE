package com.example.demo.repository.repositoryInterface;

import com.example.demo.entity.ProductAddOn;

import java.util.List;

public interface IProductAddonRepository {
    List<ProductAddOn> findProductAddonById(long id);
    public ProductAddOn insertProductAddon(ProductAddOn productAddOn,int productid);

    void deleteProductAddon(int id);

    public List<ProductAddOn> findAllProductAddonByID(String ids);
    public ProductAddOn deleteAddOnsByProductId(int productId);
    public ProductAddOn updateProductAddon(ProductAddOn productAddOn);
}
