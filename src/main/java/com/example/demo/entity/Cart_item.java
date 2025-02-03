package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Cart_item extends General {
    private int id;
    private int quantity;
    private Cart cart;
    private Product product;
    private ProductOption option;
    private String addOns;
    private List<ProductAddOn> productAddOns;

}
