package com.example.demo.controller;

import com.example.demo.entity.Review;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.ServiceInterface.IReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private IReviewService reviewService;

    // 1. Khách hàng đánh giá shipper
    @PostMapping("/add")
    public ResponseEntity<ApiResponse<String>> addReview(@RequestBody Review review) {
        reviewService.addReview(review);
        return ResponseEntity.ok(new ApiResponse<>(200, "Review submitted successfully", null));
    }

    // 2. Shipper xem đánh giá
    @GetMapping("/shipper/{shipperId}")
    public ResponseEntity<ApiResponse<List<Review>>> getReviewsByShipperId(@PathVariable int shipperId) {
        List<Review> reviews = reviewService.getReviewsByShipperId(shipperId);
        return ResponseEntity.ok(new ApiResponse<>(200, "Success", reviews));
    }
}
