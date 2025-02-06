package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class

ProductOption extends General{
    private int id;
    private String name;
    private int quantity;
    private int productId;

}
