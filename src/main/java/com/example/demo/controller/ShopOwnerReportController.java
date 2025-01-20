package com.example.demo.controller;

import com.example.demo.dto.ReportRequest;
import com.example.demo.dto.ShopReportRequest;
import com.example.demo.entity.ReportType;
import com.example.demo.entity.ShopReport;
import com.example.demo.entity.customer_report;
import com.example.demo.entity.shop_report;
import com.example.demo.exception.AppException;
import com.example.demo.jwt.Token;
import com.example.demo.repository.ShopOwnerReportRespository;
import com.example.demo.repository.ShopReportImageRespository;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.ShopReportService;
import com.example.demo.service.ShopService;
import com.example.demo.util.FileUpload;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/shop-reports")
public class ShopOwnerReportController {
    @Autowired
    ShopReportImageRespository shopReportImageRespository;
    @Autowired
    ShopReportService shopReportService;
    @Autowired
    ShopOwnerReportRespository shopOwnerReportRespository;
    @Autowired
    Token tokenService;
    @Autowired
    ShopService shopService;

    @PostMapping("/submit-report")
    public ResponseEntity<ShopReport> submitReport(
            @RequestParam("report") String reportJson,
            @RequestParam(value = "images", required = false) List<MultipartFile> images) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ShopReportRequest reportRequest = objectMapper.readValue(reportJson, ShopReportRequest.class);

            int shopID = tokenService.getIdfromToken();
            int shopid = shopService.getIdByOwnerId(shopID);
            ShopReport shopReport = new ShopReport();
            shopReport.setReportContent(reportRequest.getReportContent());
            shopReport.setShopID(shopid);
            shopReport.setReportResponse("");
            ReportType reportType = new ReportType();
            reportType.setId(reportRequest.getTypeId());
            shopReport.setReportType(reportType);
            ShopReport savedReport = shopReportService.saveShopReport(shopReport);

            int reportId = savedReport.getId();

            if (images != null && !images.isEmpty()) {
                for (MultipartFile image : images) {
                    String mimeType = image.getContentType();
                    if (!"image/jpeg".equals(mimeType) && !"image/png".equals(mimeType)) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
                    }
                    FileUpload fileUpload = new FileUpload();
                    String filePath = fileUpload.uploadImage(image);
                    shopReportImageRespository.saveShopImage(filePath, reportId);
                }
            }

            return ResponseEntity.ok(savedReport);
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<ShopReport>> getAllShopReports() {
        List<ShopReport> reports = shopReportService.getAllShopReports();
        return ResponseEntity.ok(reports);
    }

    @GetMapping("/get-notify")
    public ResponseEntity getNotifyShopReport() throws AppException {
        int id = tokenService.getIdfromToken();
        int sid = shopService.getIdByOwnerId(id);
        List<shop_report> list = shopOwnerReportRespository.getSeen(sid);
        ApiResponse apiResponse = new ApiResponse(200, "success", list);
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/save-seen")
    public ResponseEntity saveSeenShopReport(@RequestBody List<shop_report> reports) {
        for (shop_report report : reports) {
            shopOwnerReportRespository.saveSeen(report.getId());
        }
        ApiResponse apiResponse = new ApiResponse(200, "success", null);
        return ResponseEntity.ok(apiResponse);

    }

    @GetMapping("/get-response")
    public ResponseEntity getResponse () throws AppException {
        int id = tokenService.getIdfromToken();
        int sid = shopService.getIdByOwnerId(id);
        List<ShopReport> list =shopOwnerReportRespository.getAllShopReportsResponse(sid);
        ApiResponse apiResponse = new ApiResponse(200, "success", list);
        return ResponseEntity.ok(apiResponse);

    }

    @PostMapping("/post-issues")
    public  ResponseEntity postIssues(@RequestBody shop_report shopReport) throws AppException {
        int id = tokenService.getIdfromToken();
        int sid = shopService.getIdByOwnerId(id);
        shopOwnerReportRespository.insertWithResponse(shopReport,sid);
        ApiResponse apiResponse = new ApiResponse(200, "success", null);
        return ResponseEntity.ok(apiResponse);
    }

}
