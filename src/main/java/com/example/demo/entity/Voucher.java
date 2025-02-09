package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Voucher extends General {
    private int id;
    private String code;
    private String description;
    private int discountAmount;
    private int minOrderAmount;
    private String startDate;
    private String endDate;
    private int quantity;
    private Shop shop;

}
