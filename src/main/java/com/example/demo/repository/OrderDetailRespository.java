package com.example.demo.repository;

import com.example.demo.entity.OrderDetail;
import com.example.demo.mapper.OrderDetailMapper;
import com.example.demo.repository.repositoryInterface.IOrderDetailResposiotry;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderDetailRespository extends AbstractRepository<OrderDetail> implements IOrderDetailResposiotry {
    @Override
    public int saveOrderDetail(OrderDetail orderDetail) {
        String sql = "INSERT INTO `order_detail` (`order_id`, `product_id`, `product_option_name`, `product_option_id`, " +
                "`quantity`, `unit_price`) " +
                "VALUES (?,?,?,?,?,?)";
        return super.saveAndReturnId(sql,
                orderDetail.getOrder().getId(),
                orderDetail.getProduct().getId(),
                orderDetail.getProductOption().getName(),
                orderDetail.getProductOption().getId(),
                orderDetail.getQuantity(),
                orderDetail.getProduct().getPrice());
    }

    public List<OrderDetail> getProductOrderSuccess(int id){
        String sql = "SELECT * FROM order_detail AS od \n" +
                "JOIN `order` AS o ON od.order_id = o.order_id\n" +
                "JOIN product AS p ON p.product_id = od.product_id\n" +
                "WHERE o.order_status = 3 and od.is_feedback=0 and o.customer_id = ? order by o.create_at desc";
        return super.findAll(sql, new OrderDetailMapper(), id);
    }

    public OrderDetail getOrderProductDetail(int id){
        String sql = "SELECT * FROM order_detail AS o JOIN product AS p ON p.product_id = o.product_id where o.order_detail_id = ?";
        return super.findOne(sql, new OrderDetailMapper(), id);
    }

    public boolean updateFeedback(int id) {
        String sql = "Update order_detail set is_feedback = 1 where order_detail_id = ?";
        return super.save(sql, id);
    }





}
