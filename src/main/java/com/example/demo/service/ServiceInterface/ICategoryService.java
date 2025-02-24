package com.example.demo.service.ServiceInterface;
import com.example.demo.dto.ProductCategoryDto;
import com.example.demo.entity.Category;
import com.example.demo.entity.Product;
import com.example.demo.exception.AppException;

import java.util.List;

public interface ICategoryService {
    public Category createCategory(Category category) throws AppException;
    List<Category> getAllCategories();
    Category getCategoryById(int id);
    public Category updateCategory(Integer id, Category category) throws AppException;
    public void deleteCategory(int id) throws AppException;
    public List<ProductCategoryDto> getProductsByCategoryId(int categoryId);
    public int checkCategoryStatus(int categoryId);
}
