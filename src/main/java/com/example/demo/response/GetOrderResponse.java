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
public class GetOrderResponse {
    private Shop shop;
    private List<OrderList> orderList;
    private int shipCost;
    private int costStatus;
}
