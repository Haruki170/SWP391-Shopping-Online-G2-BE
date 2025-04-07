package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Shipper extends General {
    private int id;
    private String name;
    private String avatar;
    private String phone;
    private int status;
    private String identity;
    private String birthday;
    private String address;
    private String password;
}
