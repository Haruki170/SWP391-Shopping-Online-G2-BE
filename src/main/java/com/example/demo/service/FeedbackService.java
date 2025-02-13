package com.example.demo.service;

import com.example.demo.entity.FeedBack;
import com.example.demo.entity.FeedBackStatic;
import com.example.demo.entity.FeedbackImage;
import com.example.demo.repository.FeedBackRespotory;
import com.example.demo.repository.FeedbackImageRepository;
import com.example.demo.service.ServiceInterface.IFeedBackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FeedbackService implements IFeedBackService {
    @Autowired
    FeedBackRespotory feedBackRespotory;
    @Autowired
    private FeedbackImageService feedbackImageService;
    @Autowired
    private FeedbackImageRepository feedbackImageRepository;
    public double getAvgFeedBackByProductID(int product_id) {
        double avg = feedBackRespotory.getAvgFeedBackByProductID(product_id);
        double decimalPart = avg - Math.floor(avg);
        if (decimalPart <= 0.25) {
            avg = Math.floor(avg);
        } else if (decimalPart > 0.25 && decimalPart <= 0.75) {
            avg = Math.floor(avg) + 0.5;
        } else {
            avg = Math.ceil(avg);
        }
        return avg;
    }

    public List<FeedBackStatic> getFeedBackStaticByProductID(int product_id){
        int sum = feedBackRespotory.CountFeedBackByProductID(product_id);
        int sum1 = feedBackRespotory.CountFeedBackByRatingandProductId(1,product_id);
        int sum2 = feedBackRespotory.CountFeedBackByRatingandProductId(2,product_id);
        int sum3 = feedBackRespotory.CountFeedBackByRatingandProductId(3,product_id);
        int sum4 = feedBackRespotory.CountFeedBackByRatingandProductId(4,product_id);
        int sum5 = feedBackRespotory.CountFeedBackByRatingandProductId(5,product_id);
        List<FeedBackStatic> a = new ArrayList<FeedBackStatic>();
        a.add(new FeedBackStatic((sum1*1.0/sum)*100,1));
        a.add(new FeedBackStatic((sum2*1.0/sum)*100,2));
        a.add(new FeedBackStatic((sum3*1.0/sum)*100,3));
        a.add(new FeedBackStatic((sum4*1.0/sum)*100,4));
        a.add(new FeedBackStatic((sum5*1.0/sum)*100,5));
        return a ;
    }

    public List<FeedBack> findAllByProductID(int product_id) {
        List<FeedBack> feedbackList = feedBackRespotory.findAllByProductID(product_id);
        for (FeedBack feedback : feedbackList) {
            List<FeedbackImage> images = feedbackImageService.findImagesByFeedbackId(feedback.getId());
            feedback.setFeedbackImages(images);
        }
        return feedbackList;
    }

    public List<FeedBack> getFilteredFeedback(int productId, Integer rating , boolean hasImages) {
        return feedBackRespotory.filterFeedbackByRating(productId, rating,hasImages);
    }
    public boolean deleteFeedbackById(int feedbackId) {
        feedbackImageRepository.deleteByFeedbackId((long) feedbackId);
        int result = feedBackRespotory.deleteFeedbackById(feedbackId);
        return result > 0;
    }

    public boolean updateFeedback(FeedBack feedback) {
        int result = feedBackRespotory.updateFeedback(feedback);
        return result > 0;
    }
    public List<FeedBack> findLatestFeedbacksByProductId(int productId, int limit) {
        return feedBackRespotory.findLatestFeedbacksByProductId(productId, limit);
    }
    public List<FeedBack> findAllByCustomerId(int customerId) {
        return feedBackRespotory.findAllByCustomerId(customerId);
    }
    public long addFeedback(FeedBack feedback) {
        return feedBackRespotory.saveFeedback(feedback);
    }
    public FeedBack findById(Long feedbackId) {
        return feedBackRespotory.findById(feedbackId);
    }

}
