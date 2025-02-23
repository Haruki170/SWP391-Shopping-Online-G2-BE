package com.example.demo.service;

import com.example.demo.entity.FeedbackImage;
import com.example.demo.exception.AppException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.repository.FeedbackImageRepository;
import com.example.demo.service.ServiceInterface.IFeedbackImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackImageService implements IFeedbackImageService {

    @Autowired
    private FeedbackImageRepository feedbackImageRepository;

    //Lưu ảnh feedback
    public boolean saveFeedbackImage(String imageContent, Long feedbackId) {
        int result = feedbackImageRepository.saveFeedbackImage(imageContent, feedbackId);
        return result > 0;
    }

    //Lấy danh sách ảnh theo feedbackId
    public List<FeedbackImage> findImagesByFeedbackId(Long feedbackId) {
        return feedbackImageRepository.findImagesByFeedbackId(feedbackId);
    }

    //Xóa ảnh feedback theo feedbackImageId
    public boolean deleteFeedbackImage(Long feedbackImageId) throws AppException {
        int result = feedbackImageRepository.deleteFeedbackImage(feedbackImageId);
        if(result <= 0){
            throw new AppException(ErrorCode.SERVER_ERR);
        }
        return true;
    }
    public boolean existsByImagePath(String imagePath) {
        return feedbackImageRepository.existsByImagePath(imagePath);
    }

}
