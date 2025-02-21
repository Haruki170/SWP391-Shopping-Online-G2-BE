package com.example.demo.dto;

import com.example.demo.entity.Address;
import com.example.demo.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaymentDto {
    private List<OrderDto> orders;
    private Address address;
    private Customer customer;
    private String note;
    private String discountCode = "";
    private Integer discountAmount = 0;
}
