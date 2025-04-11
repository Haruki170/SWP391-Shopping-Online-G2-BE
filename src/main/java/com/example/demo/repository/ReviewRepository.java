package com.example.demo.repository;

import com.example.demo.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findByShipperId(int shipperId);
    List<Review> findByShipperIdAndRatingGreaterThanEqual(int shipperId, int rating);
    List<Review> findByShipperIdAndCommentContainingIgnoreCase(int shipperId, String keyword);
}

