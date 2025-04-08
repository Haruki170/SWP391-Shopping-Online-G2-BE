package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int rating; // 1-5 sao
    private String comment;
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "shipper_id")
    private Shipper shipper;

    // Getters, Setters
}

