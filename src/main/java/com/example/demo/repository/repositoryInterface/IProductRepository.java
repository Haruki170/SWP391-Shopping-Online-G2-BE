package com.example.demo.repository.repositoryInterface;

import com.example.demo.entity.Product;
import com.example.demo.entity.ProductAddOn;
import com.example.demo.entity.ProductImage;
import com.example.demo.entity.ProductOption;

import java.util.List;
import java.util.Map;

public interface IProductRepository {
    List<Product> findProductsById(int id);

    public List<Product> findAll(int shopid);
    public Product findById(int id);
    public boolean insert(Product product ,int shopid);
    public Product update(Product product);
    public void delete(Product product);
    public List<Product> findByCart(int id);
    public List<Product> findByWishList(int id);
    public int findShopIDbyProductID(int productID);
    public int getLastId();
    public List<Product> findProductByCategoryHot(int cID);
    public List<Product>  findAllProductByShopAdmin(int shopId);
    public boolean updateStatus(int id, int status);
    Product updateStatus(Product product);
    public Product FindCategoryByProductID(int productId);
    public Product FindOAByID(int id);
    List<Product> findProductsByProvince(String province);

    public List<Map<String, Object>> findByNameContaining(String productName);
    public List<Product> findByFilters(int category, String province, int minRating, double minPrice, String search, String sortOrder, int page, int size);
    public Product findByIdAll(int id);
}
