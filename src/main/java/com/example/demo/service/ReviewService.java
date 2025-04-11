package com.example.demo.service.ServiceInterface;

import com.example.demo.entity.Review;

import java.util.List;

public interface ReviewService {
    void addReview(Review review);
    List<Review> getReviewsByShipperId(int shipperId);
}

package com.example.demo.service;

import com.example.demo.entity.Review;
import com.example.demo.repository.ReviewRepository;
import com.example.demo.service.ServiceInterface.IReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements IReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public void addReview(Review review) {
        reviewRepository.save(review);
    }

    @Override
    public List<Review> getReviewsByShipperId(int shipperId) {
        return reviewRepository.findByShipperId(shipperId);
    }
}
