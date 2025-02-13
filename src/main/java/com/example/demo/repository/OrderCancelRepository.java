package com.example.demo.repository;

import com.example.demo.dto.OrderCancelDto;
import com.example.demo.entity.OrderCancel;
import com.example.demo.entity.OrderCancelImage;
import com.example.demo.mapper.OrderCancelImageMapper;
import com.example.demo.mapper.OrderCancelMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderCancelRepository extends AbstractRepository<OrderCancel> {
    public int insert(OrderCancelDto orderCancelDto) {
        String sql = "INSERT INTO order_request (order_id, shop_id, reason, status) \n" +
                "VALUES (?,?,?,0);\n";
        return super.saveAndReturnId(sql, orderCancelDto.getOrder_id(), orderCancelDto.getShop_id(), orderCancelDto.getReason());

    }

    public boolean insertImage(String path, int id){
        String sql = "INSERT INTO order_request_image (image, order_request_id) \n" +
                "VALUES (?,?);";
        return super.save(sql, path, id);
    }

    public int CountOrderByShopId(int shop_id) {
        String sql = "SELECT count(*) FROM order_request WHERE shop_id = ? and status =0";
        return super.selectCount(sql, shop_id);
    }

    public List<OrderCancel> getRequestByShopId(int shop_id) {
        String sql = "SELECT * FROM order_request AS r JOIN `order` AS o ON\n" +
                "r.order_id = o.order_id JOIN shop AS s\n" +
                "ON s.shop_id = r.shop_id where r.shop_id = ? ";
        return super.findAll(sql,new OrderCancelMapper(), shop_id);
    }

    public List<OrderCancelImage> getImageByOrderCancelId(int order_cancel_id) {
        String sql = "SELECT * FROM order_request_image WHERE order_request_id = ? ";
        AbstractRepository a = new AbstractRepository();
        return  a.findAll(sql,new OrderCancelImageMapper(), order_cancel_id);
    }

    public boolean updateStatus(int order_cancel_id, int status) {
        String sql = "UPDATE order_request SET status = ? WHERE id = ? ";
        return super.save(sql, status, order_cancel_id);
    }
}
