package com.example.demo.repository;

import com.example.demo.connect.DBContext;
import com.example.demo.entity.OrderTransaction;
import com.example.demo.mapper.OrderTransactionMapper;
import com.example.demo.repository.repositoryInterface.IOrderTransactionRespository;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class OrderTransactionRespository extends AbstractRepository<OrderTransaction> implements IOrderTransactionRespository {
    @Override
    public int save(OrderTransaction orderTransaction) {
        String sql = "INSERT INTO order_transaction (value) VALUES (?)";
        int generatedId = 0; // Biến để lưu ID được sinh ra

        try (Connection con = DBContext.getConnect();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, orderTransaction.getValue()); // Gán giá trị vào PreparedStatement
            ps.executeUpdate(); // Thực hiện chèn

            // Lấy ID vừa chèn
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    generatedId = rs.getInt(1); // Lấy ID từ ResultSet
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Xử lý ngoại lệ
        }

        return generatedId; // Trả về ID đã được sinh ra
    }

    @Override
    public OrderTransaction getOrderTransactionById(int id) {
        String sql = "Select * from order_transaction where id = ?";
        return  super.findOne(sql,new OrderTransactionMapper(),id);
    }

    @Override
    public boolean deleteOrderTransactionById(int id) {
        String sql = "Delete from order_transaction where id = ?";
        return super.delete(sql,id);
    }

}
