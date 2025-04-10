package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "shippers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Shipper1 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String phoneNumber;
    private String vehicleNumber;
    private Boolean available;
}
