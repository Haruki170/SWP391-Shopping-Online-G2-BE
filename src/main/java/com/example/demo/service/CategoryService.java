package com.example.demo.service;

import com.example.demo.dto.ProductCategoryDto;
import com.example.demo.entity.Category;
import com.example.demo.exception.AppException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ServiceInterface.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategoryService implements ICategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private FeedbackService feedbackService;

    // Tạo mới một category
    @Override
    public Category createCategory(Category category) throws AppException {
        return categoryRepository.createCategory(category);
    }

    // Lấy tất cả categories
    @Override
    public List<Category> getAllCategories() {
        List<Category> categories = categoryRepository.getAllCategories();
        List<Category> cat = null;
        for (Category c : categories) {
            cat = categoryRepository.getSubcategoriesByParentId(c.getId());
            c.setSubcategories(cat);
        }
        return categories;
    }
    // Lấy category theo ID
    @Override
    public Category getCategoryById(int id) {
        Category category = categoryRepository.getCategoryById(id);
        if (category != null) {
            category.setSubcategories(categoryRepository.getSubcategoriesByParentId(id));
        }
        return category;
    }

    // Cập nhật category
    @Override
    public Category updateCategory(Integer id, Category category) throws AppException {
        Category existingCategory = categoryRepository.getCategoryById(id);
        existingCategory.setName(category.getName());
        existingCategory.setStatus(category.getStatus());
        existingCategory.setParent(category.getParent());
        existingCategory.setSubcategories(categoryRepository.getSubcategoriesByParentId(existingCategory.getId()));
        return categoryRepository.updateCategory(existingCategory);
    }

    // Xóa category
    @Override
    public void deleteCategory(int id) throws AppException {
        if (!categoryRepository.existsById(id)) {
            throw new AppException(ErrorCode.CATEGORY_NOT_FOUND);
        }
        List<Category> subcategories = categoryRepository.getSubcategoriesByParentId(id);
        if (!subcategories.isEmpty()) {
            throw new AppException(ErrorCode.CATEGORY_HAS_SUBCATEGORIES);
        }

        categoryRepository.deleteCategory(id);
    }

    // Lấy ra list Product theo category
    public List<ProductCategoryDto> getProductsByCategoryId(int categoryId) {
        List<ProductCategoryDto> list = categoryRepository.getProductsByCategoryId(categoryId);

        for (ProductCategoryDto p : list) {
            p.setRating(feedbackService.getAvgFeedBackByProductID(p.getId()));
        }
        return list;
    }

    @Override
    public int checkCategoryStatus(int categoryId) {
        return categoryRepository.checkCategoryStatus(categoryId);
    }
}
