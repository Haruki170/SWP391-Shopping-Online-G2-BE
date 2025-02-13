package com.example.demo.repository;

import com.example.demo.connect.DBContext;
import com.example.demo.entity.*;
import com.example.demo.repository.repositoryInterface.IFeedBackRespository;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Repository
public class FeedBackRespotory implements IFeedBackRespository {

    @Override
    public List<FeedBack> findAllByProductID(int product_id) {
        List<FeedBack> feedBacks = new ArrayList<>();
        Connection con = null;
        Statement statement = null;
        List<FeedBack> list = new ArrayList<>();
        try {
            con = DBContext.getConnect();
            statement = con.createStatement();
            String sql = "SELECT * " +
                    "FROM customer_feedback cf JOIN customer c ON c.customer_id = cf.customer_id" +
                    " WHERE product_id =" + product_id;
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                 FeedBack feedBack = new FeedBack();
                 feedBack.setId(rs.getLong("feedback_id"));
                 feedBack.setRating(rs.getInt("feedback_rating"));
                 feedBack.setComment(rs.getString("feedback_comment"));
                 feedBack.setCreate_at(rs.getString("create_at"));
                Customer customer = new Customer();
                customer.setId(rs.getInt("customer_id"));
                customer.setName(rs.getString("customer_name"));
                customer.setAvatar(rs.getString("customer_avatar"));
                feedBack.setCustomer(customer);
                feedBacks.add(feedBack);
            }
            return feedBacks;
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (statement != null) statement.close();
                if (con != null) con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return feedBacks;
    }
    //lấy ra trung bình số feedback trong 1 product
    public double getAvgFeedBackByProductID(int product_id) {
        Double avgFeedBack = 0.0;
        Connection con = null;
        Statement statement = null;
        List<FeedBack> list = new ArrayList<>();
        try {
            con = DBContext.getConnect();
            statement = con.createStatement();
            String sql = "SELECT \n" +
                    "    COALESCE(SUM(feedback_rating), 0) AS total_rating, \n" +
                    "    COUNT(feedback_id) AS total_feedback \n" +
                    "FROM \n" +
                    "    customer_feedback \n" +
                    "WHERE \n" +
                    "    product_id = "+product_id;
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
               double sum = rs.getInt(1);
               double count = rs.getInt(2);
                avgFeedBack = sum / count ;
            }
            return avgFeedBack;
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (statement != null) statement.close();
                if (con != null) con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return 0;
    }
    //lấy được số lượng feedback trong 1 product
    public int CountFeedBackByProductID(int product_id) {
        Connection con = null;
        Statement statement = null;
        try {
            con = DBContext.getConnect();
            statement = con.createStatement();
            String sql = "SELECT  COUNT(feedback_id) " +
                    "FROM customer_feedback" +
                    " WHERE `product_id` = " + product_id;
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
               return rs.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (statement != null) statement.close();
                if (con != null) con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return 0;
    }

    //lấy được số lượng feedback theo rating
    public int CountFeedBackByRatingandProductId(int rating,int product_id) {
        Connection con = null;
        Statement statement = null;
        try {
            con = DBContext.getConnect();
            statement = con.createStatement();
            String sql = "SELECT COUNT(feedback_id) " +
                    "FROM customer_feedback" +
                    " WHERE `product_id` = "+product_id+"  and `feedback_rating`="+rating+" " ;
                      ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (statement != null) statement.close();
                if (con != null) con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return 0;
    }
    public List<FeedBack> filterFeedbackByRating(int productId, Integer rating  ,boolean hasImages) {
        List<FeedBack> feedbackList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DBContext.getConnect();
            String sql = "SELECT f.*, i.imageContent FROM customer_feedback f LEFT JOIN feedback_images i ON f.feedback_id = i.feedback_id WHERE f.product_id = ?";

            if (rating != null) {
                sql += " AND f.feedback_rating = ?";
            }

            if (hasImages) {
                sql += " AND i.imageContent IS NOT NULL";  // This ensures feedback has images
            }

            statement = connection.prepareStatement(sql);
            statement.setInt(1, productId);

            if (rating != null) {
                statement.setInt(2, rating);
            }

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                FeedBack feedback = new FeedBack();
                feedback.setId(resultSet.getLong("feedback_id"));
                feedback.setRating(resultSet.getInt("feedback_rating"));
                feedback.setComment(resultSet.getString("feedback_comment"));

                Customer customer = new Customer();
                customer.setId(resultSet.getInt("customer_id"));
                feedback.setCustomer(customer);

                feedbackList.add(feedback);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return feedbackList;
    }
    public int deleteFeedbackById(int feedbackId) {
        String sql = "DELETE FROM customer_feedback WHERE feedback_id = ?";
        try (Connection con = DBContext.getConnect();
             PreparedStatement statement = con.prepareStatement(sql)) {

            statement.setInt(1, feedbackId);
            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int updateFeedback(FeedBack feedback) {
        String sql = "UPDATE customer_feedback SET feedback_comment = ?, update_at = ? WHERE feedback_id = ?;";
        try (Connection con = DBContext.getConnect();
             PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, feedback.getComment());
            statement.setTimestamp(2, java.sql.Timestamp.valueOf(LocalDateTime.now()));
            statement.setLong(3, feedback.getId());

            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<FeedBack> findLatestFeedbacksByProductId(int productId, int limit) {
        List<FeedBack> feedbacks = new ArrayList<>();
        String sql = "SELECT * FROM customer_feedback WHERE product_id = ? ORDER BY create_at DESC LIMIT ?";
        try (Connection con = DBContext.getConnect();
             PreparedStatement statement = con.prepareStatement(sql)) {

            statement.setInt(1, productId);
            statement.setInt(2, limit);

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    FeedBack feedback = new FeedBack();
                    feedback.setId(rs.getLong("feedback_id"));
                    feedback.setRating(rs.getInt("feedback_rating"));
                    feedback.setComment(rs.getString("feedback_comment"));
                    feedback.setCreate_at(rs.getString("create_at"));
                    feedbacks.add(feedback);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return feedbacks;
    }
    public List<FeedBack> findAllByCustomerId(int customerId) {
        List<FeedBack> feedbacks = new ArrayList<>();
        String sql = "SELECT cf.*, p.product_name, pi.feedback_image_content,pi.feedback_image_id " +
                "FROM customer_feedback cf " +
                "LEFT JOIN product p ON cf.product_id = p.product_id " +
                "LEFT JOIN feedback_image pi ON cf.feedback_id = pi.feedback_id " +
                "WHERE cf.customer_id = ?";
        try (Connection con = DBContext.getConnect();
             PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setInt(1, customerId);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    FeedBack feedback = new FeedBack();
                    feedback.setId(rs.getLong("feedback_id"));
                    feedback.setRating(rs.getInt("feedback_rating"));
                    feedback.setComment(rs.getString("feedback_comment"));
                    feedback.setCreate_at(rs.getString("create_at"));
                    feedback.setProductId(rs.getInt("product_id"));
                    if (rs.getString("product_name") != null) {
                        Product product = new Product();
                        product.setName(rs.getString("product_name"));
                        feedback.setProduct(product);
                    }
                    List<FeedbackImage> images = new ArrayList<>();
                    if (rs.getString("feedback_image_content") != null) {
                        FeedbackImage image = new FeedbackImage();
                        image.setFeedbackImageId(rs.getLong("feedback_image_id"));
                        image.setImageContent(rs.getString("feedback_image_content"));
                        images.add(image);
                    }
                    feedback.setFeedbackImages(images);

                    feedbacks.add(feedback);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return feedbacks;
    }


    public int saveFeedback(FeedBack feedback) {
        String sql = "INSERT INTO customer_feedback (feedback_rating, feedback_comment, customer_id, product_id, create_at) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = DBContext.getConnect();
             PreparedStatement statement = con.prepareStatement(sql)) {

            statement.setInt(1, feedback.getRating());
            statement.setString(2, feedback.getComment());
            statement.setLong(3, feedback.getCustomer().getId());
            statement.setLong(4, feedback.getProductId());
            statement.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));

            return statement.executeUpdate();//1//0
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getLastId() {
        String sql = "SELECT feedback_id FROM customer_feedback ORDER BY feedback_id DESC LIMIT 1";
        Connection con = null;
        Statement statement = null;
        ResultSet rs = null;

        try {
            con = DBContext.getConnect();
            statement = con.createStatement();
            rs = statement.executeQuery(sql);

            if (rs.next()) {
                return rs.getInt("feedback_id");
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
    public FeedBack findById(Long feedbackId) {
        String sql = "SELECT * FROM customer_feedback WHERE feedback_id = ?";
        FeedBack feedback = null;

        try (Connection con = DBContext.getConnect();
             PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setLong(1, feedbackId);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    feedback = new FeedBack();
                    feedback.setId(rs.getLong("feedback_id"));
                    feedback.setRating(rs.getInt("feedback_rating"));
                    feedback.setComment(rs.getString("feedback_comment"));
                    feedback.setCreate_at(rs.getString("create_at"));

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return feedback;

    }



}
