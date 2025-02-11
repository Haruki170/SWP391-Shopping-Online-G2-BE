package com.example.demo.mapper;

import com.example.demo.entity.Customer;
import com.example.demo.entity.Order;
import com.example.demo.entity.Shop;
import com.example.demo.entity.TransactionAdmin;

import java.sql.ResultSet;

public class AdminTransactionMapper implements RowMapper<TransactionAdmin>{
    @Override
    public TransactionAdmin mapRow(ResultSet rs) {
        TransactionAdmin admin = new TransactionAdmin();
        try {
            admin.setId(rs.getInt("id"));
            admin.setAmount(rs.getInt("amount"));
            admin.setIsPaid(rs.getInt("is_paid"));
            admin.setType(rs.getInt("type"));
            admin.setNetAmount(rs.getInt("net_amount"));
            admin.setShipCost(rs.getInt("ship_cost"));
            admin.setCreate_at(rs.getString("created_at"));
            admin.setDescription(rs.getString("description"));
            Order order = new Order();
            order.setId(rs.getInt("order_id"));
            order.setOrder_status(rs.getInt("order_status"));
            order.setOrderTotal(rs.getInt("order_total"));
            order.setCode(rs.getString("order_code"));
            order.setPayment_status(rs.getInt("payment_status"));

            Shop shop = new Shop();
            shop.setId(rs.getInt("shop_id"));
            shop.setName(rs.getString("shop_name"));

            Customer c = new Customer();
            c.setId(rs.getInt("customer_id"));
            c.setEmail(rs.getString("customer_email"));
            admin.setShop(shop);
            admin.setOrder(order);
            admin.setCustomer(c);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return admin;
    }
}
