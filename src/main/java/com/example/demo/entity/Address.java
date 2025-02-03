package com.example.demo.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Address extends General {
    private int id;
    private String province;
    private String ward;
    private String district;
    private String address;
    private String phone;
    private String name;
    private int isDefault;
    private String nameReceiver;
}
