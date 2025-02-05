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
public class Order extends General {
    private int id;
    private String code;
    private Customer customer;
    private int orderTotal;
    private int payment_status;
    private int order_status;
    private Address address;
    private int shipCost;
    private Shop shop;
    private int payment;
    private List<OrderDetail> orderDetails;
    private String note;
}
