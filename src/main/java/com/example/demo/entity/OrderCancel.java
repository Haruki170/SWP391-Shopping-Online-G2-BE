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
public class OrderCancel extends General{
    private int id;
    private String reason;
    private int status;
    private Order order;
    private Shop shop;
    private List<OrderCancelImage> images;
}


