package com.example.demo.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class  Product extends General {
    private int id;
    private String name;
    private int price;
    private String avatar;
    private String description;
    private String specifications;
    private int status;
    private List<Category> categories;
    private Shop shop;
    private Double weight;
    private Double height;
    private Double length;
    private Double width;
    private List<FeedBack> feedBacks;


    private Double rating;
    private List<ProductOption> options;
    private List<ProductAddOn> addOns;
    private List<ProductImage> images;



    public double getAverageRating() {
        if (feedBacks == null || feedBacks.isEmpty()) {
            return 0;
        }
        double totalRating = 0;
        for (FeedBack feedback : feedBacks) {
            totalRating += feedback.getRating();
        }
        return totalRating / feedBacks.size();
    }
}
