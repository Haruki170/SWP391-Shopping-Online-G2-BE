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
public class ShopReport {
    private int id;
    private String reportContent;
    private String reportResponse;
    private Customer customer;
    private ReportType reportType;
    private List<ShopReportImage> shopReportImages;
    private int customerId;
    private ShopReportType reportType1;
    private int shopID;
    private Shop shop;

}
