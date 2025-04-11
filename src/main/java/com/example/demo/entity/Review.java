package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int rating; // Số sao: 1-5
    private String comment;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "shipper_id")
    private Shipper shipper;

    // Có thể thêm người đánh giá nếu cần: ví dụ userId hoặc tên người đánh giá
    // private String reviewerName;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
}

