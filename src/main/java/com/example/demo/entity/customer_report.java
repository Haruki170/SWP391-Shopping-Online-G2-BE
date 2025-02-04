package com.example.demo.entity;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class customer_report {
    private int reportId;
    private String reportContent;
    private String email;
    private ReportType loaiPhanHoi;
    private String reportResponse;
    private ReportType type;
    private int customerId;
    private customer_report_type customerReportType;
    private List<customer_report_image> customerReportImages;
    private Customer customer;
}
