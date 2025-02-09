package com.example.demo.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TransactionAdmin extends General {
    private int id;
    private int amount;
    private String description;
    private int netAmount;
    private int type;
    private int isPaid;
    private int shipCost;
    private Shop shop;
    private Customer customer;
    private Order order;
}
