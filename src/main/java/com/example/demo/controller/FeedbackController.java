package com.example.demo.controller;

import com.example.demo.entity.Customer;
import com.example.demo.entity.FeedBack;
import com.example.demo.entity.FeedBackStatic;
import com.example.demo.exception.AppException;
import com.example.demo.jwt.Token;
import com.example.demo.repository.FeedBackRespotory;
import com.example.demo.repository.OrderDetailRespository;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.FeedbackImageService;
import com.example.demo.service.FeedbackService;
import com.example.demo.util.FileUpload;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {
    @Autowired
    private FeedbackService feedbackService;
    @Autowired
    private FeedbackImageService feedbackImageService;
    @Autowired
    private FeedBackRespotory feedBackRespotory;
    @Autowired
    OrderDetailRespository orderDetailRespoitory;
    @Autowired
    private Token token;
    @Autowired
    OrderDetailRespository orderDetailRespository;
    @GetMapping("/get-avg/{id}")
    public ResponseEntity getAvgFeedBack(@PathVariable int id) {
        double a = feedbackService.getAvgFeedBackByProductID(id);
        return  ResponseEntity.ok().body(a);
    }


    @GetMapping("/getPercent/{id}")
    public List<FeedBackStatic>getPercent(@PathVariable int id) {
        return feedbackService.getFeedBackStaticByProductID(id);
    }
    @GetMapping("/getAllFeedBack/{id}")
    public List<FeedBack> getAllFeedBack(@PathVariable int id) {
        return feedbackService.findAllByProductID(id);
    }
    @GetMapping("/filter")
    public ResponseEntity<List<FeedBack>> filterFeedbackByRating(
            @RequestParam("productId") int productId,
            @RequestParam(value = "rating", required = false) Integer rating,
            @RequestParam(value = "hasImages", required = false, defaultValue = "false") boolean hasImages) {
        List<FeedBack> filteredFeedback = feedbackService.getFilteredFeedback(productId, rating, hasImages);
        return ResponseEntity.ok(filteredFeedback);
    }

    @DeleteMapping("/delete/{feedbackId}")
    public ResponseEntity<String> deleteFeedback(@PathVariable int feedbackId) {
        boolean result = feedbackService.deleteFeedbackById(feedbackId);
        if (result) {
            return ResponseEntity.ok("Feedback deleted successfully!");
        } else {
            return ResponseEntity.status(500).body("Failed to delete feedback");
        }
    }
    @PutMapping("/update")
    public ResponseEntity<String> updateFeedback(@RequestBody FeedBack feedback) {
        boolean result = feedbackService.updateFeedback(feedback);
        if (result) {
            return ResponseEntity.ok("Feedback updated successfully!");
        } else {
            return ResponseEntity.status(500).body("Failed to update feedback");
        }
    }
    @GetMapping("/latest/{productId}")
    public List<FeedBack> getLatestFeedbacks(@PathVariable int productId, @RequestParam("limit") int limit) {
        return feedbackService.findLatestFeedbacksByProductId(productId, limit);
    }
    @PostMapping("/add/{oid}")
    public ResponseEntity<Long> addFeedback(
            @RequestParam("feedback") String feedbackJson,
            @RequestParam(value = "images", required = false) List<MultipartFile> images,
            @PathVariable int oid
           ) {

        try {
            FeedBack feedback = new ObjectMapper().readValue(feedbackJson, FeedBack.class);
            int id = token.getIdfromToken();
            Customer c = new Customer();
            c.setId(id);
            feedback.setCustomer(c);
            long feedbackId = feedbackService.addFeedback(feedback);
            if(feedbackId > 0){
                orderDetailRespository.updateFeedback(oid);
            }
            int lastId = feedBackRespotory.getLastId();
//            if(lastId != 0){
//                orderDetailRespository.updateFeedback(order);
//            }
            if (images != null && !images.isEmpty()) {
                for (MultipartFile image : images) {
                    String mimeType = image.getContentType();
                    if (!"image/jpeg".equals(mimeType) && !"image/png".equals(mimeType)) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(null);
                    }
                    FileUpload fileUpload = new FileUpload();
                    String path = fileUpload.uploadImage(image);
                    feedbackImageService.saveFeedbackImage(path, (long) lastId);
                }
            }
            return ResponseEntity.ok(feedbackId);

        } catch (JsonProcessingException e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }
    @GetMapping("/customer/all")
    public ResponseEntity<List<FeedBack>> getAllFeedbackByCustomerId() {
        int customerId = token.getIdfromToken();
        List<FeedBack> feedbacks = feedbackService.findAllByCustomerId(customerId);
        return ResponseEntity.ok(feedbacks);
    }

    @PostMapping("/remove-image/{id}")
    public ResponseEntity RemoveImage(@PathVariable int id) throws AppException {
        feedbackImageService.deleteFeedbackImage((Long.parseLong(id+"")));
        return ResponseEntity.ok(new ApiResponse<>(200, "success", null));

    }

    @PutMapping("/edit/{feedbackId}")
    public ResponseEntity<Long> editFeedback(
            @PathVariable Long feedbackId,
            @RequestParam("feedback") String feedbackJson,
            @RequestParam(value = "images", required = false) List<MultipartFile> images,
            @RequestParam(value = "deleteImages", required = false) List<String> deleteImages) {
        try {
            FeedBack updatedFeedback = new ObjectMapper().readValue(feedbackJson, FeedBack.class);
            FeedBack existingFeedback = feedbackService.findById(feedbackId);
            if (existingFeedback == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            updatedFeedback.setId(existingFeedback.getId());
            updatedFeedback.setRating(existingFeedback.getRating());
            updatedFeedback.setCustomer(existingFeedback.getCustomer());
            feedbackService.updateFeedback(updatedFeedback);

            // Xóa ảnh
            if (deleteImages != null && !deleteImages.isEmpty()) {
                for (String imagePath : deleteImages) {
                    if (feedbackImageService.existsByImagePath(imagePath)) {
                        feedbackImageService.deleteFeedbackImage(Long.valueOf(imagePath));
                    } else {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(null);
                    }
                }
            }

            if (images != null && !images.isEmpty()) {
                for (MultipartFile image : images) {
                    String mimeType = image.getContentType();
                    if (!"image/jpeg".equals(mimeType) && !"image/png".equals(mimeType)) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Cũng sửa ở đây
                    }
                    FileUpload fileUpload = new FileUpload();
                    String path = fileUpload.uploadImage(image);
                    feedbackImageService.saveFeedbackImage(path, feedbackId);
                }
            }

            return ResponseEntity.ok(feedbackId);

        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

}
