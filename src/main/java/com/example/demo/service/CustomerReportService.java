package com.example.demo.service;

import com.example.demo.entity.customer_report;
import com.example.demo.entity.customer_report_image;
import com.example.demo.exception.AppException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.mapper.CustomerReportMapper;
import com.example.demo.repository.CustomerReportImageRepository;
import com.example.demo.repository.CustomerReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class CustomerReportService {

    @Autowired
    private final CustomerReportRepository customerReportRepository;
    @Autowired
    private CustomerReportImageRepository customerReportImageRepository;

    @Autowired
    public CustomerReportService(CustomerReportRepository customerReportRepository) {
        this.customerReportRepository = customerReportRepository;
    }

    // Thêm  một report
    public boolean addReport(customer_report report) throws AppException {
        if (report.getReportContent() == null || report.getReportContent().isEmpty()) {
            throw new AppException(ErrorCode.INVALID_CODE);
        }
        if (!customerReportRepository.insertReport(report)) {
            throw new AppException(ErrorCode.REPORT_CREATION_FAILED);
        }
        return true;
    }

    // Lấy một report theo ID
    public customer_report getReportById(int reportId) throws AppException {
        if (reportId <= 0) {
            throw new AppException(ErrorCode.INVAlID_ID);
        }
        return customerReportRepository.findById(reportId)
                .orElseThrow(() -> new AppException(ErrorCode.REPORT_NOT_FOUND));
    }

    // Cập nhật report
    public boolean updateReport(customer_report report) throws AppException {
        if (report.getReportId() <= 0) {
            throw new AppException(ErrorCode.INVAlID_ID);
        }
        if (!customerReportRepository.checkExists(report.getReportId())) {
            throw new AppException(ErrorCode.REPORT_NOT_FOUND);
        }
        if (!customerReportRepository.updateReport(report)) {
            throw new AppException(ErrorCode.REPORT_UPDATE_FAILED);
        }
        return true;
    }


    // Xóa report theo ID
    public boolean deleteReportById(int reportId) throws AppException {
        if (reportId <= 0) {
            throw new AppException(ErrorCode.INVAlID_ID);
        }
        if (!customerReportRepository.checkExists(reportId)) {
            throw new AppException(ErrorCode.REPORT_NOT_FOUND);
        }
        if (!customerReportRepository.deleteById(reportId)) {
            throw new AppException(ErrorCode.REPORT_DELETE_FAILED);
        }
        return true;
    }

    // Lấy tất cả report
    public List<customer_report> getAllReports() throws AppException {
        List<customer_report> reports = customerReportRepository.findAllReports();
        if (reports.isEmpty()) {
            throw new AppException(ErrorCode.REPORT_NOT_FOUND);
        }
        return reports;
    }

    public customer_report saveReportWithImages(customer_report report, List<MultipartFile> images) throws IOException {
        customer_report savedReport = customerReportRepository.save(report);
        List<customer_report_image> reportImages = new ArrayList<>();
        for (MultipartFile image : images) {
            customer_report_image reportImage = new customer_report_image();
            reportImage.setReportImageContent(new String(image.getBytes()));
            reportImage.setCustomerReport(savedReport);
            reportImages.add(reportImage);
        }

        customerReportImageRepository.saveAll(reportImages);
        return savedReport;
    }
    public List<customer_report> getAllCustomerReports() {
        return customerReportRepository.findAllReportsAdmin();
    }
    public customer_report saveCustomerReport(customer_report report) {
        return customerReportRepository.save(report);
    }
    public int getLastReportID() {
        return customerReportRepository.getLastReportID();
    }
}
