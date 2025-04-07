package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@NoArgsConstructor
@ToString
@Getter
@Setter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShipperAuth {
    private String name;
    private String phone;
    private String identity;
    private String oldPassword;
    private String password;
    private String confirmPassword;
}
