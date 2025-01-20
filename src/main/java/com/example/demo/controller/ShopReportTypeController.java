package com.example.demo.controller;

import com.example.demo.entity.ShopReportType;
import com.example.demo.service.ShopReportTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/shop-report-type")
public class ShopReportTypeController {

    @Autowired
    private ShopReportTypeService shopReportTypeService;

    @GetMapping("/all")
    public ResponseEntity<List<ShopReportType>> getAllReportTypes() {
        List<ShopReportType> reportTypes = shopReportTypeService.getAllReportTypes();
        return ResponseEntity.ok(reportTypes);
    }
}

