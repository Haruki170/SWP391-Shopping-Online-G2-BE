package com.example.demo.mapper;

import com.example.demo.entity.Order;
import com.example.demo.entity.OrderDetail;
import com.example.demo.entity.Product;
import com.example.demo.entity.ProductOption;

import java.sql.ResultSet;

public class OrderDetailMapper implements RowMapper<OrderDetail> {

    @Override
    public OrderDetail mapRow(ResultSet rs) {
        OrderDetail orderDetail = new OrderDetail();
        try {
            orderDetail.setId(rs.getInt("order_detail_id"));
            orderDetail.setQuantity(rs.getInt("quantity"));
            orderDetail.setPrice(rs.getInt("unit_price"));
            orderDetail.setProductOptionName(rs.getString("product_option_name"));
            Product p = new Product();
            p.setId(rs.getInt("product_id"));
            p.setName(rs.getString("product_name"));
            p.setAvatar(rs.getString("product_avatar"));
            orderDetail.setProduct(p);

            Order o = new Order();
            o.setId(rs.getInt("order_id"));
            o.setCode(rs.getString("order_code"));
            o.setCreate_at(rs.getString("create_at"));

            orderDetail.setOrder(o);

        }catch (Exception e) {
            e.printStackTrace();
        }
        return  orderDetail;
    }
}
