package com.example.demo.dto;


import com.example.demo.entity.Order;
import com.example.demo.entity.Shipper;
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
public class ShipperOrderDto {
    private Shipper shipper;
    private Order order;
    private int status;
    private String note;
}