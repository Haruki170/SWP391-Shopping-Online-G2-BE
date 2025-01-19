package com.example.demo.controller;

import com.example.demo.entity.FeedbackImage;
import com.example.demo.exception.AppException;
import com.example.demo.service.FeedbackImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feedback-images")
public class FeedbackImageController {

    @Autowired
    private FeedbackImageService feedbackImageService;

    //Lưu ảnh feedback
    @PostMapping("/add")
    public ResponseEntity<String>addFeedbackImage(@RequestParam("imageContent") String imageContent,
                                                   @RequestParam("feedbackId") long feedbackId) {
        boolean result = feedbackImageService.saveFeedbackImage(imageContent, feedbackId);
        if (result) {
            return ResponseEntity.ok("Image added successfully!");
        } else {
            return ResponseEntity.status(500).body("Failed to add image");
        }
    }
    //Lấy danh sách ảnh theo feedbackId
    @GetMapping("/{feedbackId}")
    public ResponseEntity<List<FeedbackImage>> getImagesByFeedbackId(@PathVariable Long feedbackId) {
        List<FeedbackImage> images = feedbackImageService.findImagesByFeedbackId(feedbackId);
        return ResponseEntity.ok(images);
    }

    // Xóa ảnh feedback theo feedbackImageId
    @DeleteMapping("/delete/{imageId}")
    public ResponseEntity<String> deleteFeedbackImage(@PathVariable Long imageId) throws AppException {
        boolean result = feedbackImageService.deleteFeedbackImage(imageId);
        if (result) {
            return ResponseEntity.ok("Image deleted successfully!");
        } else {
            return ResponseEntity.status(500).body("Failed to delete image");
        }
    }
}
