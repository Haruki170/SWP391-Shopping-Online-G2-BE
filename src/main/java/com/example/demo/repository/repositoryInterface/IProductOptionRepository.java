package com.example.demo.repository.repositoryInterface;

import com.example.demo.entity.ProductAddOn;
import com.example.demo.entity.ProductOption;

import java.util.List;

 public interface IProductOptionRepository {
     List<ProductOption> findProductOptionById(long id);
     List<ProductOption> getAll();
     public ProductOption insertProductOption(ProductOption productOption, int productid);

     ProductOption updateProductOption(ProductOption productOption);

     void deleteProductOption(int id);

     public ProductOption deleteOptionsByProductId(int productid);

 }
