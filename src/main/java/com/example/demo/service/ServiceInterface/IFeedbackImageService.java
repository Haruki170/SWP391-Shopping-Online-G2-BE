package com.example.demo.service.ServiceInterface;

import com.example.demo.entity.FeedbackImage;
import com.example.demo.exception.AppException;

import java.util.List;

public interface IFeedbackImageService {
    public boolean saveFeedbackImage(String imageContent, Long feedbackId);
    public List<FeedbackImage> findImagesByFeedbackId(Long feedbackId);
    public boolean deleteFeedbackImage(Long feedbackImageId) throws AppException;
    public boolean existsByImagePath(String imagePath);
}
