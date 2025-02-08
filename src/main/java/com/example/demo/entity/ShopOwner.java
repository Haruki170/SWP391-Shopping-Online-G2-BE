package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class ShopOwner extends General {
    private int id;
    private String email;
    private String province;
    private String district;
    private String ward;
    private String avatar;
    private String password;
    private String address;
    private String phone;
    private int status;
    private String name;
    private String identification;
    private String front;
    private String back;
    private String fogotPassword;
}
