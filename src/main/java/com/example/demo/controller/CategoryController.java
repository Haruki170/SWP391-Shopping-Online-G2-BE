package com.example.demo.controller;

import com.example.demo.dto.ProductCategoryDto;
import com.example.demo.entity.Category;
import com.example.demo.entity.Product;
import com.example.demo.exception.AppException;
import com.example.demo.repository.AbstractRepository;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.response.ApiResponse;
import com.example.demo.response.CategoryResponse;
import com.example.demo.service.ServiceInterface.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;
    @Autowired
    private CategoryRepository categoryRepository;
    // Tạo một category
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Category>> createCategory(@RequestBody Category category) {
        try {
            Category createdCategory = categoryService.createCategory(category);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(201, "Category added successfully", createdCategory));
        } catch (AppException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(400, e.getErrorCode().getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(500, "Internal Server Error", null));
        }
    }

    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getAllCategory() {
            List<CategoryResponse> list = categoryRepository.getAllCategory();
            return ResponseEntity.ok(new ApiResponse<>(200,"success", list));

    }

    //Lấy tất cả categories
    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse<List<Category>>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        if (categories.isEmpty()) {
            return ResponseEntity.ok(new ApiResponse<>(204, "No categories found", categories));
        }
        return ResponseEntity.ok(new ApiResponse<>(200, "Categories retrieved successfully", categories));
    }



    // Lấy category theo ID
    @GetMapping("/getbyID/{id}")

    public ResponseEntity<ApiResponse<Category>> getCategoryById(@PathVariable int id) {
        Category category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "Category retrieved successfully", category));
    }
    //update category
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<Category>> updateCategory(@PathVariable Integer id, @RequestBody Category category) {
        try {
            Category updatedCategory = categoryService.updateCategory(id, category);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(200, "Category updated successfully", updatedCategory));
        } catch (AppException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(400, e.getErrorCode().getMessage(), null));
        }
    }
    // Xóa category theo ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(@PathVariable int id) {
        try {
            categoryService.deleteCategory(id);
            return ResponseEntity.ok(new ApiResponse<>(200, "Category deleted successfully", null));
        } catch (AppException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(404, e.getErrorCode().getMessage(), null));
        }
    }
    @GetMapping("/products")
    public ResponseEntity getProductsByCategory(@RequestParam("id") int categoryId) {
        System.out.println(categoryId);
        List<ProductCategoryDto> list= categoryService.getProductsByCategoryId(categoryId);
        return ResponseEntity.ok(new ApiResponse<>(200, "success",list ));
    }

    @GetMapping("/status/{id}")
    public ResponseEntity<Integer> getCategoryStatus(@PathVariable int id) {
        int status = categoryService.checkCategoryStatus(id);
        if (status == 1) {
            return ResponseEntity.ok(status);
        } else if (status == 0) {
            return ResponseEntity.ok(status);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
