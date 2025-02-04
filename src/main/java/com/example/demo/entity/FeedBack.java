package com.example.demo.entity;

import lombok.ToString;

import java.util.List;
@ToString
public class FeedBack extends General {
    Long id;
    int rating ;
    String comment;
    private List<FeedbackImage> feedbackImages;
    Customer customer;
    private int productId;
    private Product product;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<FeedbackImage> getFeedbackImages() {
        return feedbackImages;
    }

    public void setFeedbackImages(List<FeedbackImage> feedbackImages) {
        this.feedbackImages = feedbackImages;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
