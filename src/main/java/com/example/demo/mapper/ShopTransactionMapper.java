package com.example.demo.mapper;

import com.example.demo.entity.Customer;
import com.example.demo.entity.Order;
import com.example.demo.entity.Shop;
import com.example.demo.entity.TransactionShop;

import java.sql.ResultSet;

public class ShopTransactionMapper implements RowMapper<TransactionShop> {

    @Override
    public TransactionShop mapRow(ResultSet rs) {
        TransactionShop shopTransaction = new TransactionShop();
        try {
            shopTransaction.setId(rs.getInt("id"));
            shopTransaction.setAmount(rs.getInt("amount"));
            shopTransaction.setDescription(rs.getString("description"));
            shopTransaction.setType(rs.getInt("type"));
            shopTransaction.setIsCommisson(rs.getInt("is_paid_commission"));
            shopTransaction.setNetAmount(rs.getInt("net_amount"));
            shopTransaction.setCreate_at(rs.getString("created_at"));
            Shop shop = new Shop();
            shop.setId(rs.getInt("shop_id"));
            shop.setName(rs.getString("shop_name"));
            Customer customer = new Customer();
            customer.setId(rs.getInt("customer_id"));
            customer.setEmail(rs.getString("customer_email"));
            Order order = new Order();
            order.setId(rs.getInt("order_id"));
            order.setCode(rs.getString("order_code"));
            order.setOrder_status(rs.getInt("order_status"));
            order.setOrderTotal(rs.getInt("order_total"));
            order.setShipCost(rs.getInt("ship_cost"));
            shopTransaction.setShop(shop);
            shopTransaction.setCustomer(customer);
            shopTransaction.setOrder(order);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return shopTransaction;
    }
}
