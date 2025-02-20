package com.example.demo.response;

import com.example.demo.entity.Cart_item;
import com.example.demo.entity.Shop;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CartResponse {
    private Shop shop;
    List<Cart_item> cart_items;

}
