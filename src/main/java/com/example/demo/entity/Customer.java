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
public class Customer extends Account {
    private int customerId;
    private String name;
    private String avatar;
    private String fogotPassword;
    private String refeshToken;
    private String phoneNumber;
    private int status;
    List<customer_report> customerReports;
}
