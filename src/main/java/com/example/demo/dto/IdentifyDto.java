package com.example.demo.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class IdentifyDto {
    private  int id;
    private String name;
    private String number;
    private String province;
    private String district;
    private String ward;

}
