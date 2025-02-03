package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopReportRequest {
    private String reportContent;
    private String email;
    private int typeId;
}
