package com.example.demo.dto;


import com.example.demo.entity.ShopOwner;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ShopDto {
    private int id;
    private String name;
    private String logo;
    private String decs;
    private String taxNumber;
    private int productActive;
    private int productInactive;
    private double Rating;
    private String createAt;
    private ShopOwner owner;
    private int newTransaction;
    private int newOrderRequest;
}
