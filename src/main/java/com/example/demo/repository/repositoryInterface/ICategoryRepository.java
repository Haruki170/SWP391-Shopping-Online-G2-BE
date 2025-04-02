package com.example.demo.repository.repositoryInterface;

import com.example.demo.dto.ProductCategoryDto;
import com.example.demo.entity.Category;
import com.example.demo.entity.Product;

import java.util.List;

public interface ICategoryRepository {

    //Tạo mới một category
    Category createCategory(Category category);

    //Lấy tất cả categories
    List<Category> getAllCategories();

    //Lấy category theo ID
    Category getCategoryById(int id);

    //Cập nhật category
    Category updateCategory(Category category);

    // Xóa category
    void deleteCategory(int id);
    public List<Category> getCategoryByProduct(int id);
    //lấy ra list Product theo category
    List<ProductCategoryDto> getProductsByCategoryId(int categoryId);
    int checkCategoryStatus(int categoryId);
    List<Category> getSubcategoriesByParentId(int parentId);
    List<Category> searchCategories(String name, Integer status, Integer parent);
}
