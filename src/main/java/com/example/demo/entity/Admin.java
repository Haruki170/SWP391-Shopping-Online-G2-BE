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

public class Admin extends General {
    private int id;
    private String name;
    private String email;
    private String password;
    private String avatar;
    private String phone;
    private String address;
    private String district;
    private String ward;
    private String province;
    private int status;
    int role;
    private String fogotPassword;
    List<Role> roles;
}
