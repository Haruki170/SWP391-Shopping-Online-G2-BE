package com.example.demo.response;

import com.example.demo.entity.Product;
import com.example.demo.entity.ProductAddOn;
import com.example.demo.entity.ProductOption;
import lombok.*;

import java.util.List;
import java.util.Optional;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrderList {
    private int cartId;
    private Product product;
    private ProductOption productOption;
    private String addOns;
    private List<ProductAddOn> productAddOns;
    private int quantity;

}
