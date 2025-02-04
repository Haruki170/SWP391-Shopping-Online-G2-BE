package com.example.demo.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FeedbackImage extends General {
    private Long feedbackImageId;
    private String imageContent;
    private FeedBack feedback;
}
