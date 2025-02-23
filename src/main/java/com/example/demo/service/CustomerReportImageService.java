package com.example.demo.service;

import com.example.demo.repository.CustomerReportImageRepository;
import com.example.demo.entity.customer_report_image;
import com.example.demo.repository.CustomerReportImageRepository;
public class CustomerReportImageService {
    private  CustomerReportImageRepository customerReportImageRepository;


   public boolean saveReportImage(String reportContent,int reportID){
       System.out.println(reportContent);
       int result = customerReportImageRepository.saveReportImage(reportContent, reportID);
       return result >0;

   }
}
