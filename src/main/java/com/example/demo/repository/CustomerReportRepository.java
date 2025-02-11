package com.example.demo.repository;

import com.example.demo.connect.DBContext;
import com.example.demo.entity.Customer;
import com.example.demo.entity.customer_report;
import com.example.demo.entity.customer_report_image;
import com.example.demo.entity.customer_report_type;
import com.example.demo.mapper.CustomerReportMapper;
import com.example.demo.repository.repositoryInterface.ICustomerReportRepository;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CustomerReportRepository extends AbstractRepository<customer_report> implements ICustomerReportRepository {
    @Override
    public List<customer_report> findAllReports() {
        String sql = "SELECT * FROM customer_report";
        return findAll(sql, new CustomerReportMapper());
    }

    @Override
    public boolean insertReport(customer_report report) {
        String sql = "INSERT INTO customer_report (report_content, customer_id, create_at, update_at, email, loai_phan_hoi) " +
                "VALUES (?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, ?, ?)";
        return super.save(sql, report.getReportContent(), report.getCustomerId(), report.getEmail(), report.getLoaiPhanHoi().getId());
    }

    @Override
    public boolean updateReport(customer_report report) {
        String sql = "UPDATE customer_report SET report_content = ?, customer_id = ?, update_at = CURRENT_TIMESTAMP, email = ?, loai_phan_hoi = ? " +
                "WHERE report_id = ?";
        return super.save(sql, report.getReportContent(), report.getCustomerId(), report.getEmail(), report.getLoaiPhanHoi().getId(), report.getReportId());
    }

    @Override
    public boolean deleteById(int reportId) {
        String sql = "DELETE FROM customer_report WHERE report_id = ?";
        return super.save(sql, reportId);
    }

    @Override
    public boolean checkExists(int reportId) {
        String sql = "SELECT 1 FROM customer_report WHERE report_id = ?";
        List<customer_report> results = findAll(sql, new CustomerReportMapper(), reportId);
        return !results.isEmpty();
    }

    @Override
    public Optional<customer_report> findById(int reportId) {
        String sql = "SELECT * FROM customer_report WHERE report_id = ?";
        List<customer_report> results = findAll(sql, new CustomerReportMapper(), reportId);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    public customer_report save(customer_report report) {
        String sql = "INSERT INTO customer_report (report_content, customer_id, type_id) " +
                "VALUES (?, ?, ?)";
        try (Connection connection = DBContext.getConnect();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, report.getReportContent());
            statement.setInt(2, report.getCustomerId());

            statement.setInt(3, report.getType().getId());

            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        report.setReportId(generatedKeys.getInt(1));
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("err");
            e.printStackTrace();
        }

        return report;
    }

    public List<customer_report> findAllReportsAdmin() {
        List<customer_report> reports = new ArrayList<>();
        Connection con = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            con = DBContext.getConnect();
            statement = con.createStatement();
            String sql = """
                    SELECT cr.report_id, cr.report_content, cr.report_response, 
                           c.customer_id, c.customer_name AS customer_name, c.customer_avatar AS customer_avatar, 
                           crt.type AS report_type, cri.report_image_content 
                    FROM customer_report cr 
                    JOIN customer c ON cr.customer_id = c.customer_id 
                    JOIN customer_report_type crt ON cr.type_id = crt.id 
                    LEFT JOIN customer_report_image cri ON cr.report_id = cri.report_id
                    """;
            rs = statement.executeQuery(sql);
            while (rs.next()) {

                customer_report report = new customer_report();
                report.setReportId(rs.getInt("report_id"));
                report.setReportContent(rs.getString("report_content"));

                report.setReportResponse(rs.getString("report_response"));

                Customer customer = new Customer();
                customer.setCustomerId(rs.getInt("customer_id"));
                customer.setName(rs.getString("customer_name"));
                customer.setAvatar(rs.getString("customer_avatar"));
                report.setCustomer(customer);

                customer_report_type reportType = new customer_report_type();
                reportType.setType(rs.getString("report_type"));
                report.setCustomerReportType(reportType);

                List<customer_report_image> images = report.getCustomerReportImages() != null ? report.getCustomerReportImages() : new ArrayList<>();
                String imageContent = rs.getString("report_image_content");
                if (imageContent != null) {
                    customer_report_image image = new customer_report_image();
                    image.setReportImageContent(imageContent);
                    images.add(image);
                }
                report.setCustomerReportImages(images);
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

    @Override
    public int getLastReportID() {
        String sql = "SELECT report_id FROM customer_report ORDER BY report_id DESC LIMIT 1";
        Connection con = null;
        Statement statement = null;
        ResultSet rs = null;

        try {
            con = DBContext.getConnect();
            statement = con.createStatement();
            rs = statement.executeQuery(sql);

            if (rs.next()) {
                return rs.getInt("report_id");
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
        return 0;
    }

    @Override
    public boolean saveResponse(customer_report report) {
        String sql = "Update customer_report set report_response= ? , status = 1 where report_id=? ";
        return super.save(sql, report.getReportResponse(), report.getReportId());
    }

    @Override
    public boolean saveSeen(int id) {
        String sql = "Update customer_report set status = 0 where report_id=? ";
        return super.save(sql, id);
    }

    public List<customer_report> getSeen(int id) {
        System.out.println(id);
        String sql = "select * from customer_report where status = 1 and customer_id=? ";
        return super.findAll(sql, new CustomerReportMapper(), id);
    }

    public List<customer_report> getResponseList(int id) {
        String sql = "select * from customer_report where report_response is not null and customer_id=? ";
        return super.findAll(sql, new CustomerReportMapper(), id);
    }
    public List<customer_report> findAllReportsAdmin3(int id) {
        List<customer_report> reports = new ArrayList<>();
        Connection con = null;
        PreparedStatement preparedStatement = null; // Sử dụng PreparedStatement thay vì Statement
        ResultSet rs = null;

        try {
            con = DBContext.getConnect();

            // SQL query with a parameter placeholder
            String sql = """
                SELECT cr.report_id, cr.report_content, cr.report_response, 
                       c.customer_id, c.customer_name AS customer_name, c.customer_avatar AS customer_avatar, 
                       crt.type AS report_type, cri.report_image_content 
                FROM customer_report cr 
                JOIN customer c ON cr.customer_id = c.customer_id 
                JOIN customer_report_type crt ON cr.type_id = crt.id 
                LEFT JOIN customer_report_image cri ON cr.report_id = cri.report_id
                WHERE c.customer_id = ? AND cr.report_response IS NOT NULL
                """;

            // Prepare the statement and set the parameter
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, id); // Set the value of the parameter

            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                customer_report report = new customer_report();
                report.setReportId(rs.getInt("report_id"));
                report.setReportContent(rs.getString("report_content"));
                report.setReportResponse(rs.getString("report_response"));

                Customer customer = new Customer();
                customer.setCustomerId(rs.getInt("customer_id"));
                customer.setName(rs.getString("customer_name"));
                customer.setAvatar(rs.getString("customer_avatar"));
                report.setCustomer(customer);

                customer_report_type reportType = new customer_report_type();
                reportType.setType(rs.getString("report_type"));
                report.setCustomerReportType(reportType);

                List<customer_report_image> images = report.getCustomerReportImages() != null ? report.getCustomerReportImages() : new ArrayList<>();
                String imageContent = rs.getString("report_image_content");
                if (imageContent != null) {
                    customer_report_image image = new customer_report_image();
                    image.setReportImageContent(imageContent);
                    images.add(image);
                }
                report.setCustomerReportImages(images);
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


}