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
@Override
public List<Review> searchReviews(int shipperId, String keyword) {
    return reviewRepository.findByShipperIdAndCommentContainingIgnoreCase(shipperId, keyword);
}

@Override
public List<Review> filterReviewsByRating(int shipperId, int minRating) {
    return reviewRepository.findByShipperIdAndRatingGreaterThanEqual(shipperId, minRating);
}

@Override
public List<Review> getSortedReviews(int shipperId, String sortBy, String direction) {
    Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
    return reviewRepository.findAll((root, query, cb) -> cb.equal(root.get("shipper").get("id"), shipperId), sort);
}
