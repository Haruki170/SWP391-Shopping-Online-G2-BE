package com.example.demo.mapper;



import com.example.demo.entity.Address;
import com.example.demo.entity.Customer;
import com.example.demo.entity.Order;
import com.example.demo.entity.Shop;

import java.sql.ResultSet;

public class OrderMapper implements RowMapper<Order> {

    @Override
    public Order mapRow(ResultSet rs) {
        Order order = new Order();
        try {
            order.setId(rs.getInt("order_id"));
            order.setCode(rs.getString("order_code"));
            order.setOrderTotal(rs.getInt("order_total"));
            order.setOrder_status(rs.getInt("order_status"));
            order.setPayment_status(rs.getInt("payment_status"));
            order.setShipCost(rs.getInt("ship_cost"));
            order.setPayment(rs.getInt("payment_method_id"));
            order.setCreate_at(rs.getString("create_at"));
            order.setUpdate_at(rs.getString("update_at"));
            order.setDiscount(rs.getInt("discount"));
            Address address = new Address();
            address.setProvince(rs.getString("province"));
            address.setDistrict(rs.getString("district"));
            address.setWard(rs.getString("ward"));
            address.setNameReceiver(rs.getString("name_receiver"));
            address.setPhone(rs.getString("phone_receiver"));
            address.setAddress(rs.getString("address"));

            Customer customer = new Customer();
            customer.setEmail(rs.getString("customer_email"));
            order.setCustomer(customer);
            order.setAddress(address);

            Shop shop = new Shop();
            shop.setName(rs.getString("shop_name"));
            shop.setId(rs.getInt("shop_id"));

            order.setShop(shop);
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return order;
    }
}
