package com.example.demo.service.ServiceInterface;

import com.example.demo.entity.FeedBack;
import com.example.demo.entity.FeedBackStatic;

import java.util.List;

public interface IFeedBackService {
    public double getAvgFeedBackByProductID(int product_id);
    public List<FeedBack> findAllByProductID(int product_id);
    public List<FeedBackStatic> getFeedBackStaticByProductID(int product_id);
    public List<FeedBack> getFilteredFeedback(int productId, Integer rating , boolean hasImages);
    public boolean deleteFeedbackById(int feedbackId);
    public boolean updateFeedback(FeedBack feedback);
    public List<FeedBack> findLatestFeedbacksByProductId(int productId, int limit);
    public List<FeedBack> findAllByCustomerId(int customerId);
    public long addFeedback(FeedBack feedback);
    public FeedBack findById(Long feedbackId);
}
