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
public class ShipOrder extends General {
    private int id;
    private Shipper shipper;
    private Order order;
    private String note;
    private int status;
}

