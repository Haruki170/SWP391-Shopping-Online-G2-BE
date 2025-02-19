package com.example.demo.service;

import com.example.demo.entity.ShopReportType;
import com.example.demo.entity.customer_report_type;
import com.example.demo.repository.ShopReportTypeRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopReportTypeService {
    @Autowired
    ShopReportTypeRespository shopReportTypeRespository;
    public List<ShopReportType> getAllReportTypes() {
        return shopReportTypeRespository.findAll();
    }
}
