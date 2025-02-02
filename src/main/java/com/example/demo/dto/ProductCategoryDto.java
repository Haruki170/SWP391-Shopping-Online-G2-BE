package com.example.demo.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ProductCategoryDto {
    private int id;
    private String name;
    private String avatar;
    private int price;
    private double rating;

}
