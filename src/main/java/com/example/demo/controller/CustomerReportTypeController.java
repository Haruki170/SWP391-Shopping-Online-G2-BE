package com.example.demo.controller;

import com.example.demo.service.ReportTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.demo.entity.customer_report_type;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/customer-report-type")
public class CustomerReportTypeController {

    @Autowired
    private ReportTypeService reportTypeService;
    @GetMapping("/report-types")
    public ResponseEntity<List<customer_report_type>> getReportTypes() {
        List<customer_report_type> reportTypes = reportTypeService.getAllReportTypes();
        return ResponseEntity.ok(reportTypes);
    }
}
