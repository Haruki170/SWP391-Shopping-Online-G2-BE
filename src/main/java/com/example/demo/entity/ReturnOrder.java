package com.example.demo.entity;
import java.time.LocalDate;
public class ReturnOrder {

    private Long id;
    private String orderCode;
    private String reason;
    private String customerName;
    private LocalDate returnDate;
    private String status; // pending, approved, rejected

    // Getters and setters
}
