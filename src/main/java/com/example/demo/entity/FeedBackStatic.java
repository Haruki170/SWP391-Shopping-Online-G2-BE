package com.example.demo.entity;

public class FeedBackStatic {
    public int feedBack_rating;
    public double feedBack_percentage;


    public int getFeedBack_rating() {
        return feedBack_rating;
    }

    public void setFeedBack_rating(int feedBack_rating) {
        this.feedBack_rating = feedBack_rating;
    }

    public double getFeedBack_percentage() {
        return feedBack_percentage;
    }

    public void setFeedBack_percentage(double feedBack_percentage) {
        this.feedBack_percentage = feedBack_percentage;
    }

    public FeedBackStatic(double feedBack_percentage, int feedBack_rating) {
        this.feedBack_percentage = feedBack_percentage;
        this.feedBack_rating = feedBack_rating;
    }
}
