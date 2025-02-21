package com.example.demo.repository.repositoryInterface;

import com.example.demo.entity.FeedBack;

import java.util.List;

public interface IFeedBackRespository {
    public List<FeedBack> findAllByProductID(int product_id);
    public double getAvgFeedBackByProductID(int product_id);
    public int CountFeedBackByRatingandProductId(int rating,int product_id);
    public int CountFeedBackByProductID(int product_id);
    public List<FeedBack> filterFeedbackByRating(int productId, Integer rating  ,boolean hasImages);
    public int deleteFeedbackById(int feedbackId);
    public int updateFeedback(FeedBack feedback);
    public List<FeedBack> findLatestFeedbacksByProductId(int productId, int limit);
    public List<FeedBack> findAllByCustomerId(int customerId);
    public FeedBack findById(Long feedbackId);

}
