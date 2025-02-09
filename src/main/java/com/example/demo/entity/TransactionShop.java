package com.example.demo.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TransactionShop  extends General{
    private int id;
    private int amount;
    private int type;
    private int isCommisson ;
    private String description;
    private Order order;
    private Customer customer;
    private Shop shop;
    private int netAmount;

}
