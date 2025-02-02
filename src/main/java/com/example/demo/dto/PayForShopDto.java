package com.example.demo.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.checkerframework.checker.units.qual.N;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PayForShopDto {
    private int shopId;
    private String description;
    private int amount;
    private String code;
    private int tid;
}
