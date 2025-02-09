package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ShopReportImage {
    private int reportImageId;
    private String reportImageContent;
    private ShopReport report;

    public int getReportImageId() {
        return reportImageId;
    }

    public void setReportImageId(int reportImageId) {
        this.reportImageId = reportImageId;
    }

    public String getReportImageContent() {
        return reportImageContent;
    }

    public void setReportImageContent(String reportImageContent) {
        this.reportImageContent = reportImageContent;
    }

    public ShopReport getReport() {
        return report;
    }

    public void setReport(ShopReport report) {
        this.report = report;
    }
}
