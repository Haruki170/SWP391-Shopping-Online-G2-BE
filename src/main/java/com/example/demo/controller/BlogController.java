package com.example.demo.controller;

import com.example.demo.entity.Blog;
import com.example.demo.repository.BlogRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.response.ApiResponse;
import com.example.demo.response.CategoryResponse;
import com.example.demo.service.ServiceInterface.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blog")
public class BlogController {


    private BlogRespository blogRepository = new BlogRespository();

    @GetMapping("/{shopId}")
    public ResponseEntity<List<Blog>> getBlogsByShop(@PathVariable Long shopId) {
        List<Blog> blogs = blogRepository.findAllByShopId(shopId);
        return ResponseEntity.ok(blogs);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createBlog(@RequestBody Blog blog) {
        blogRepository.save("INSERT INTO blog (title, content, shop_id) VALUES (?, ?, ?)", blog.getTitle(), blog.getContent(), blog.getShopId());
        return ResponseEntity.ok(new ApiResponse(200, "success", null));
    }



    @DeleteMapping("/{shopId}")
    public ResponseEntity<ApiResponse> deleteBlog(@PathVariable Long id) {
        blogRepository.save("DELETE FROM blog WHERE id = ?", id);
        return ResponseEntity.ok(new ApiResponse(200, "success", null));
    }
}
