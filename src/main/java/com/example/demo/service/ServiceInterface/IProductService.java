package com.example.demo.service.ServiceInterface;

import com.example.demo.entity.Product;

import java.util.List;
import java.util.Map;

public interface IProductService {
    public Product findIDProduct(int id);
    List<Product> findAllProducts(int shopid);
    public Product findIDMax();
    Product addProduct(Product product ,int shopid);

    List<Product> findProductsById(int id);

    int getLastId();
    Product updateProduct(Product product);

    void deleteProduct(Product product);



    public List<Product> findProductByCategoryHot(int cID);

    Product updateProductStatus(Product product);

    public List<Map<String, Object>> searchProductsByName(String productName);
    public List<Product> findProductAdmin(int cID);
    public boolean updateStatus(int id, int status);
    public Product findIDProduct2(int id);
    public Product getOneProduct(int id);
    public List<Product> filterProducts(int category, String province, int rating, double price);
}
