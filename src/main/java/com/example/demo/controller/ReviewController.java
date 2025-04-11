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
// Tìm kiếm đánh giá theo từ khóa
@GetMapping("/shipper/{shipperId}/search")
public ResponseEntity<ApiResponse<List<Review>>> searchReviews(
        @PathVariable int shipperId,
        @RequestParam String keyword) {
    List<Review> reviews = reviewService.searchReviews(shipperId, keyword);
    return ResponseEntity.ok(new ApiResponse<>(200, "Success", reviews));
}

// Lọc đánh giá theo số sao tối thiểu
@GetMapping("/shipper/{shipperId}/filter")
public ResponseEntity<ApiResponse<List<Review>>> filterReviews(
        @PathVariable int shipperId,
        @RequestParam int minRating) {
    List<Review> reviews = reviewService.filterReviewsByRating(shipperId, minRating);
    return ResponseEntity.ok(new ApiResponse<>(200, "Success", reviews));
}

// Sắp xếp đánh giá theo trường nhất định
@GetMapping("/shipper/{shipperId}/sort")
public ResponseEntity<ApiResponse<List<Review>>> sortReviews(
        @PathVariable int shipperId,
        @RequestParam(defaultValue = "createdAt") String sortBy,
        @RequestParam(defaultValue = "desc") String direction) {
    List<Review> reviews = reviewService.getSortedReviews(shipperId, sortBy, direction);
    return ResponseEntity.ok(new ApiResponse<>(200, "Success", reviews));
}

