package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class customer_report_image {
    private int reportImageId;
    private String reportImageContent;
    private customer_report customerReport;
}
