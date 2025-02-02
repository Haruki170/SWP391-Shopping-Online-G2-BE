package com.example.demo.dto;

import com.example.demo.entity.Shop;
import com.example.demo.entity.Voucher;
import com.example.demo.response.OrderList;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrderDto {
    private Shop shop;
    private List<OrderList> orderList;
    private int shipCost;
    private int costStatus;
    private int totalCost;
    private List<Voucher> voucherList;
}
