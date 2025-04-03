package com.example.demo.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Shipper {
    private int id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private int status;
}
