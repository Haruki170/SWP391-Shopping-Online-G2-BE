package com.example.demo.controller;

import com.example.demo.dto.ReportRequest;
import com.example.demo.entity.ReportType;
import com.example.demo.entity.customer_report;
import com.example.demo.entity.customer_report_image;
import com.example.demo.exception.AppException;
import com.example.demo.jwt.Token;
import com.example.demo.repository.CustomerReportImageRepository;
import com.example.demo.repository.CustomerReportRepository;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.CustomerReportImageService;
import com.example.demo.service.CustomerReportService;
import com.example.demo.util.FileUpload;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/customer-reports")
public class CustomerReportController {
    @Autowired
    CustomerReportImageRepository customerReportImageRepository;
    private final CustomerReportService customerReportService;

    private  CustomerReportImageService customerReportImageService;
    @Autowired
    Token tokenService;
    @Autowired
    private CustomerReportRepository customerReportRepository;

    @Autowired
    public CustomerReportController(CustomerReportService customerReportService) {
        this.customerReportService = customerReportService;
    }


    @PostMapping
    public ResponseEntity<ApiResponse<Void>> addReport(@RequestBody customer_report report) {
        try {
            customerReportService.addReport(report);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(201, "Report added successfully", null));
        } catch (AppException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(400, e.getMessage(), null));
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<customer_report>> getReportById(@PathVariable int id) {
        try {
            customer_report report = customerReportService.getReportById(id);
            return ResponseEntity.ok(new ApiResponse<>(200, "Report retrieved successfully", report));
        } catch (AppException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(404, e.getMessage(), null));
        }
    }


    @PutMapping
    public ResponseEntity<ApiResponse<Void>> updateReport(@RequestBody customer_report report) {
        try {
            customerReportService.updateReport(report);
            return ResponseEntity.ok(new ApiResponse<>(200, "Report updated successfully", null));
        } catch (AppException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(400, e.getMessage(), null));
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteReportById(@PathVariable int id) {
        try {
            customerReportService.deleteReportById(id);
            return ResponseEntity.ok(new ApiResponse<>(200, "Report deleted successfully", null));
        } catch (AppException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(400, e.getMessage(), null));
        }
    }


    @GetMapping("/abc")
    public ResponseEntity<ApiResponse<List<customer_report>>> getAllReports() {
        try {
            List<customer_report> reports = customerReportService.getAllReports();
            return ResponseEntity.ok(new ApiResponse<>(200, "Reports retrieved successfully", reports));
        } catch (AppException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(404, e.getMessage(), null));
        }

    }

    @PostMapping("/submit-report")
    public ResponseEntity<customer_report> submitReport(
            @RequestParam("report") String reportJson,
            @RequestParam( value = "images", required = false) List<MultipartFile> images) throws IOException {

            System.out.println(reportJson);
            ObjectMapper objectMapper = new ObjectMapper();
            ReportRequest reportRequest = objectMapper.readValue(reportJson, ReportRequest.class);
            int customerId = tokenService.getIdfromToken();
            customer_report report = new customer_report();
            report.setReportContent(reportRequest.getReportContent());
            report.setCustomerId(customerId);
            report.setEmail(reportRequest.getEmail());
            ReportType reportType = new ReportType();
            reportType.setId(reportRequest.getTypeId());
            report.setType(reportType);
            customer_report savedReport = customerReportService.saveCustomerReport(report);
            int lastId = report.getReportId();

            if (images != null ) {
                for (MultipartFile image : images) {
                    String mimeType = image.getContentType();
                    if (!"image/jpeg".equals(mimeType) && !"image/png".equals(mimeType)) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
                    }
                    FileUpload fileUpload = new FileUpload();
                    String path = fileUpload.uploadImage(image);

                    customerReportImageRepository.saveImage(path, lastId);
                }
            }
            return ResponseEntity.ok(savedReport);

    }
    @GetMapping("/GetAll")
    public ResponseEntity<ApiResponse<List<customer_report>>> getAllReport() {
        try {
            List<customer_report> reports = customerReportService.getAllCustomerReports();
            if (reports.isEmpty()) {

                return ResponseEntity.ok(new ApiResponse<>(204, "No reports found", null));
            }

            return ResponseEntity.ok(new ApiResponse<>(200, "Reports retrieved successfully", reports));
        } catch (Exception e) {
            e.printStackTrace();

            return new ResponseEntity<>(new ApiResponse<>(500, "An error occurred", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/send-response")
    public ResponseEntity<ApiResponse> sendResponse(@RequestBody customer_report report) {
        System.out.println(report);
        customerReportRepository.saveResponse(report);
        ApiResponse<Void> response = new ApiResponse<>();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-seen")
    public ResponseEntity getSeen(){
        int id = tokenService.getIdfromToken();
        System.out.println(id);
        List<customer_report> list = customerReportRepository.getSeen(id);
        ApiResponse<List<customer_report>> response = new ApiResponse<>(200,"success", list);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/save-seen")
    public ResponseEntity saveSeen(@RequestBody List<customer_report> reports) {
        for(customer_report report : reports) {
            customerReportRepository.saveSeen(report.getReportId());
        }
        ApiResponse<List<customer_report>> response = new ApiResponse<>(200,"success", null);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/get-customer-response")
    public ResponseEntity getCustomerResponse() throws AppException {
        int id = tokenService.getIdfromToken();
        List<customer_report> list = customerReportRepository.findAllReportsAdmin3(id);
        return ResponseEntity.ok(list);
    }
}
