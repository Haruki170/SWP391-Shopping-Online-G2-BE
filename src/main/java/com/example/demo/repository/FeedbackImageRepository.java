package com.example.demo.repository;
import com.example.demo.connect.DBContext;
import com.example.demo.entity.FeedbackImage;
import com.example.demo.repository.repositoryInterface.IFeedbackImageRepository;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Repository
public class FeedbackImageRepository implements IFeedbackImageRepository {

    // Thêm mới FeedbackImage
    public int saveFeedbackImage(String imageContent, Long feedbackId) {
        String sql = "INSERT INTO feedback_image (feedback_image_content, feedback_id, create_at, update_at) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection connection = DBContext.getConnect();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, imageContent);
            statement.setLong(2, feedbackId);
            statement.setTimestamp(3, java.sql.Timestamp.valueOf(LocalDateTime.now()));
            statement.setTimestamp(4, java.sql.Timestamp.valueOf(LocalDateTime.now()));

            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


    public List<FeedbackImage> findImagesByFeedbackId(Long feedbackId) {
        List<FeedbackImage> feedbackImages = new ArrayList<>();
        String sql = "SELECT * FROM feedback_image WHERE feedback_id = ?";

        try (Connection connection = DBContext.getConnect();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, feedbackId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    FeedbackImage feedbackImage = new FeedbackImage();
                    feedbackImage.setFeedbackImageId(resultSet.getLong("feedback_image_id"));
                    feedbackImage.setImageContent(resultSet.getString("feedback_image_content"));
                    feedbackImages.add(feedbackImage);
                    System.out.println("Fetched image: " + feedbackImage.getImageContent() + " for feedbackId: " + feedbackId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return feedbackImages;
    }

    public int deleteFeedbackImage(Long feedbackImageId) {
        String sql = "DELETE FROM feedback_image WHERE feedback_image_id = ?";
        try (Connection con = DBContext.getConnect();
             PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setLong(1, feedbackImageId);
            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public int deleteByFeedbackId(Long feedbackId) {
        String sql = "DELETE FROM feedback_image WHERE feedback_id = ?";
        try (Connection con = DBContext.getConnect();
             PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setLong(1, feedbackId);
            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public boolean existsByImagePath(String imagePath) {
        String sql = "SELECT COUNT(*) FROM feedback_image WHERE feedback_image_content = ?";
        try (Connection con = DBContext.getConnect();
             PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, imagePath);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
