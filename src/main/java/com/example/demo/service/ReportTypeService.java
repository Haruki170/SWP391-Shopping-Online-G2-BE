package com.example.demo.service;
import com.example.demo.entity.ReportType;
import com.example.demo.repository.ReportTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.entity.customer_report_type;
import java.util.List;
@Service
public class ReportTypeService {
    @Autowired
    private  ReportTypeRepository reportTypeRepository;

    public List<customer_report_type> getAllReportTypes() {
        return reportTypeRepository.findAll();
    }
}
