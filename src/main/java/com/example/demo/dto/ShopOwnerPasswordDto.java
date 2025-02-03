package com.example.demo.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ShopOwnerPasswordDto {
    private String oldPassword;
    private String newPassword;
    private String confirmNewPassword;

}
