package com.example.demo.mapper;

import com.example.demo.entity.Order;
import com.example.demo.entity.OrderCancel;
import com.example.demo.entity.Shop;

import java.sql.ResultSet;

public class OrderCancelMapper implements RowMapper<OrderCancel> {

    @Override
    public OrderCancel mapRow(ResultSet rs) {
        OrderCancel orderCancel = new OrderCancel();
        try {
            orderCancel.setId(rs.getInt("id"));
            orderCancel.setReason(rs.getString("reason"));
            orderCancel.setStatus(rs.getInt("status"));
            orderCancel.setCreate_at(rs.getString("create_at"));
            Order order = new Order();
            order.setId(rs.getInt("id"));
            order.setCode(rs.getString("order_code"));
            order.setOrderTotal(rs.getInt("order_total"));
            order.setCreate_at(rs.getString("create_at"));

            Shop shop = new Shop();
            shop.setId(rs.getInt("shop_id"));
            shop.setName(rs.getString("shop_name"));
            shop.setLogo(rs.getString("shop_logo"));
            orderCancel.setOrder(order);
            orderCancel.setShop(shop);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return orderCancel;
    }
}
