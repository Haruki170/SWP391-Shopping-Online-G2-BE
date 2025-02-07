package com.example.demo.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ShipCost extends General {
    private int id;
    private double startWeight;
    private double endWeight;
    private int cost;
}
