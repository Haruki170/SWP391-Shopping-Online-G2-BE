package com.example.demo.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ShopRegister extends General {
    private int id;
    private String name;
    private String address;
    private String phone;
    private String desc;
    private String taxNumber;
    private int isOnline;
    private int status;
    List<ShopRegisterImage> images;
    Customer customer;
}
