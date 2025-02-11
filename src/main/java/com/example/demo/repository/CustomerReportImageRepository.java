package com.example.demo.repository;

import com.example.demo.connect.DBContext;
import com.example.demo.entity.customer_report_image;
import com.example.demo.mapper.CustomerReportImageMapper;
import com.example.demo.repository.repositoryInterface.ICustomerReportImageRepository;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class CustomerReportImageRepository extends AbstractRepository<customer_report_image> implements ICustomerReportImageRepository {

    @Override
    public List<customer_report_image> findAllImagesByReportId(int reportId) {
        String sql = "SELECT * FROM customer_report_image WHERE report_id = ?";
        return findAll(sql, new CustomerReportImageMapper(), reportId);
    }

    @Override
    public boolean insertImage(customer_report_image image) {
        String sql = "INSERT INTO customer_report_image (report_image_content, report_id, create_at, update_at) " +
                "VALUES (?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";
        return super.save(sql, image.getReportImageContent(), image.getCustomerReport().getReportId());
    }

    @Override
    public boolean updateImage(customer_report_image image) {
        String sql = "UPDATE customer_report_image SET report_image_content = ?, update_at = CURRENT_TIMESTAMP " +
                "WHERE report_image_id = ?";
        return super.save(sql, image.getReportImageContent(), image.getReportImageId());
    }

    @Override
    public boolean deleteImageById(int reportImageId) {
        String sql = "DELETE FROM customer_report_image WHERE report_image_id = ?";
        return super.save(sql, reportImageId);
    }

    @Override
    public Optional<customer_report_image> findImageById(int reportImageId) {
        String sql = "SELECT * FROM customer_report_image WHERE report_image_id = ?";
        List<customer_report_image> results = findAll(sql, new CustomerReportImageMapper(), reportImageId);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    @Override
    public boolean saveAll(List<customer_report_image> images) {
        String sql = "INSERT INTO customer_report_image (report_image_content, report_id) " +
                "VALUES (?, ?)";

        try (Connection connection = DBContext.getConnect();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            for (customer_report_image image : images) {
                statement.setString(1, image.getReportImageContent());
                statement.setInt(2, image.getCustomerReport().getReportId());
                statement.addBatch();
            }

            int[] updateCounts = statement.executeBatch();


            for (int count : updateCounts) {
                if (count == PreparedStatement.EXECUTE_FAILED) {
                    return false;
                }
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean saveImage(String iamge, int id){
        AbstractRepository a = new AbstractRepository();
        String sql ="INSERT INTO customer_report_image (report_image_content, report_id) VALUES (?, ?)";
        return  a.save(sql, iamge,id);
    }

    @Override
    public int saveReportImage(String imageContent, int reportID) {
        System.out.println("répo");
        System.out.println(imageContent);
        String sql = "INSERT INTO customer_report_image (report_image_content, report_id) VALUES (?, ?)";
        try (Connection connection = DBContext.getConnect();
             PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, imageContent);
            statement.setInt(2, reportID);

            int rowsAffected = statement.executeUpdate();
            System.out.println(rowsAffected);
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    }
                }
            }
            return -1;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
       }

    }
}
