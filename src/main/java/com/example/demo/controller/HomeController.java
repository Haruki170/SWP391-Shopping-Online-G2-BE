package com.example.demo.controller;

import com.example.demo.entity.Category;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/home")
public class HomeController {
    @Autowired
    HomeService homeService;

    @GetMapping("/get")
    public ResponseEntity getProduct(){
        List<Category> list =homeService.getListProductByCategory();
        ApiResponse response = new ApiResponse(200, "success",list);
        return ResponseEntity.ok(response);
    }

}
