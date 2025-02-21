package com.example.demo.repository.repositoryInterface;

import com.example.demo.entity.FeedbackImage;

import java.util.List;

public interface IFeedbackImageRepository {
    public int saveFeedbackImage(String imageContent, Long feedbackId);
    public List<FeedbackImage> findImagesByFeedbackId(Long feedbackId);
    public int deleteFeedbackImage(Long feedbackImageId);
    public int deleteByFeedbackId(Long feedbackId);
    public boolean existsByImagePath(String imagePath);
}
