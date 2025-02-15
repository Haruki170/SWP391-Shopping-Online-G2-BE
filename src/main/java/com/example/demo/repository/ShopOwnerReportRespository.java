package com.example.demo.repository;

import com.example.demo.connect.DBContext;
import com.example.demo.entity.*;
import com.example.demo.mapper.ShopReportMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ShopOwnerReportRespository extends AbstractRepository<shop_report> {
    public ShopReport save(ShopReport shopReport) {
        String sql = "INSERT INTO shop_report (report_content,  type_id , shop_id) " +
                "VALUES (?, ?, ?)";
        try (Connection connection = DBContext.getConnect()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, shopReport.getReportContent());
                statement.setInt(2, shopReport.getReportType().getId());
                statement.setInt(3,shopReport.getShopID());
                int affectedRows = statement.executeUpdate();
                if (affectedRows > 0) {
                    try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            int reportId = generatedKeys.getInt(1);
                            shopReport.setId(reportId);
                            if (shopReport.getShopReportImages() != null) {
                                String imageSql = "INSERT INTO shop_report_image (report_image_content, report_id, create_at, update_at) VALUES (?, ?, NOW(), NOW())";
                                try (PreparedStatement imageStatement = connection.prepareStatement(imageSql)) {
                                    for (ShopReportImage image : shopReport.getShopReportImages()) {
                                        imageStatement.setString(1, image.getReportImageContent());
                                        imageStatement.setInt(2, reportId);
                                        imageStatement.addBatch();
                                    }
                                    imageStatement.executeBatch();
                                }
                            }
                        }
                    }
                }

                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                System.out.println("Error saving shop report: " + e.getMessage());
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return shopReport;
    }
    public List<ShopReport> getAllShopReports() {
        List<ShopReport> reports = new ArrayList<>();
        Connection con = null;
        Statement statement = null;
        ResultSet rs = null;

        try {
            con = DBContext.getConnect();
            statement = con.createStatement();
            String sql = """
            SELECT 
                sr.id AS report_id,
                sr.report_content,
                sr.report_response,
                s.shop_id,
                s.shop_name AS shop_name,
                s.shop_logo AS shop_logo,
                srt.type AS report_type,
                sri.report_image_content
            FROM 
                shop_report sr
            JOIN 
                shop s ON sr.shop_id = s.shop_id
            JOIN 
                shop_report_type srt ON sr.type_id = srt.id
            LEFT JOIN 
                shop_report_image sri ON sr.id = sri.report_id
            """;
            rs = statement.executeQuery(sql);

            while (rs.next()) {
                ShopReport report = new ShopReport();
                report.setId(rs.getInt("report_id"));
                report.setReportContent(rs.getString("report_content"));
                report.setReportResponse(rs.getString("report_response"));

                Shop shop = new Shop();
                shop.setId(rs.getInt("shop_id"));
                shop.setName(rs.getString("shop_name"));
                shop.setLogo(rs.getString("shop_logo"));
                report.setShop(shop);

                ShopReportType reportType = new ShopReportType();
                reportType.setType(rs.getString("report_type"));
                report.setReportType1(reportType);

                List<ShopReportImage> images = report.getShopReportImages() != null ? report.getShopReportImages() : new ArrayList<>();
                String imageContent = rs.getString("report_image_content");
                if (imageContent != null) {
                    ShopReportImage image = new ShopReportImage();
                    image.setReportImageContent(imageContent);
                    images.add(image);
                }
                report.setShopReportImages(images);
                reports.add(report);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (statement != null) statement.close();
                if (con != null) con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return reports;
    }

    public boolean saveResponse(shop_report report) {
        String sql = "Update shop_report set report_response= ? , status = 1 where id=? ";
        return super.save(sql, report.getReportResponse(), report.getId());
    }


    public boolean saveSeen(int id) {
        String sql = "Update shop_report set status = 0 where id=? ";
        return super.save(sql, id);
    }

    public List<shop_report> getSeen(int id){
        System.out.println(id);
        String sql = "select * from shop_report where status = 1 and shop_id=? ";
        return super.findAll(sql, new ShopReportMapper(), id);
    }

    public List<ShopReport> getAllShopReportsResponse(int id) {
        List<ShopReport> reports = new ArrayList<>();
        Connection con = null;
        PreparedStatement preparedStatement = null; // Sử dụng PreparedStatement thay vì Statement
        ResultSet rs = null;

        try {
            con = DBContext.getConnect();
            String sql = """
            SELECT 
                sr.id AS report_id,
                sr.report_content,
                sr.report_response,
                s.shop_id,
                s.shop_name AS shop_name,
                s.shop_logo AS shop_logo,
                srt.type AS report_type,
                sri.report_image_content
            FROM 
                shop_report sr
            JOIN 
                shop s ON sr.shop_id = s.shop_id
            JOIN 
                shop_report_type srt ON sr.type_id = srt.id
            LEFT JOIN 
                shop_report_image sri ON sr.id = sri.report_id
            WHERE 
                sr.report_response IS NOT NULL AND sr.shop_id = ?
            """;

            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, id); // Thiết lập giá trị cho dấu ?

            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                ShopReport report = new ShopReport();
                report.setId(rs.getInt("report_id"));
                report.setReportContent(rs.getString("report_content"));
                report.setReportResponse(rs.getString("report_response"));

                Shop shop = new Shop();
                shop.setId(rs.getInt("shop_id"));
                shop.setName(rs.getString("shop_name"));
                shop.setLogo(rs.getString("shop_logo"));
                report.setShop(shop);

                ShopReportType reportType = new ShopReportType();
                reportType.setType(rs.getString("report_type"));
                report.setReportType1(reportType);

                List<ShopReportImage> images = report.getShopReportImages() != null ? report.getShopReportImages() : new ArrayList<>();
                String imageContent = rs.getString("report_image_content");
                if (imageContent != null) {
                    ShopReportImage image = new ShopReportImage();
                    image.setReportImageContent(imageContent);
                    images.add(image);
                }
                report.setShopReportImages(images);
                reports.add(report);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (preparedStatement != null) preparedStatement.close(); // Đóng PreparedStatement
                if (con != null) con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return reports;
    }

    public boolean insertWithResponse(shop_report shopReport, int sid) {
        String sql = "INSERT INTO shop_report (report_content, report_response, type_id, shop_id, status) " +
                "VALUES (?, ?, ?, ?, 1);";
        return super.save(sql, shopReport.getReportContent(), shopReport.getReportResponse(), shopReport.getTypeId(), sid);
    }
}
