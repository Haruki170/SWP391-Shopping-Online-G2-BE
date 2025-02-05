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
public class OrderDetail extends  General{
    private int id;
    private Order order;
    private Product product;
    private ProductOption productOption;
    private String productOptionName;
    private int quantity;
    private int price;
    private List<ProductAddOn> productAddOns;
}
