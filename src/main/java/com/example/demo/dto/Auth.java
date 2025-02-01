package com.example.demo.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@NoArgsConstructor
@ToString
@Getter
@Setter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Auth {
    private String name;
    private String email;
    private String oldPassword;
    private String password;
    private String confirmPassword;
    
}
