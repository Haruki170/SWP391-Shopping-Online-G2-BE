package com.example.demo.repository.repositoryInterface;

import com.example.demo.entity.customer_report_image;

import java.util.List;
import java.util.Optional;

public interface ICustomerReportImageRepository {
    List<customer_report_image> findAllImagesByReportId(int reportId);
    boolean insertImage(customer_report_image image);
    boolean updateImage(customer_report_image image);
    boolean deleteImageById(int reportImageId);
    Optional<customer_report_image> findImageById(int reportImageId);
    public boolean saveAll(List<customer_report_image> images);
    public int saveReportImage(String imageContent, int reportID);
}
