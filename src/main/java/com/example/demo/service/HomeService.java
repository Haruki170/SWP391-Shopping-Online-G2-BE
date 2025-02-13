package com.example.demo.service;

import com.example.demo.entity.Category;
import com.example.demo.entity.Product;
import com.example.demo.exception.ErrorCode;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeService {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    FeedbackService feedbackService;
    public List<Category> getListProductByCategory() {
        List<Category> list = categoryRepository.getCategoryParent();
        for (Category category : list) {
            List<Product> products = productRepository.findProductSaleByCate(category.getId());
            for (Product product : products) {
                product.setRating(feedbackService.getAvgFeedBackByProductID(product.getId()));
            }
            category.setProducts(products);
        }
        return list;
    }
}
