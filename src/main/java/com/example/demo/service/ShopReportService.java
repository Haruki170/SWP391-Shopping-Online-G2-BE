package com.example.demo.service;

import com.example.demo.entity.ShopReport;
import com.example.demo.entity.customer_report;
import com.example.demo.repository.ShopOwnerReportRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopReportService {
    @Autowired ShopOwnerReportRespository shopOwnerReportRespository;
    public ShopReport saveShopReport(ShopReport report) {
        return shopOwnerReportRespository.save(report);
    }
    public List<ShopReport> getAllShopReports() {
        return shopOwnerReportRespository.getAllShopReports();
    }
}
