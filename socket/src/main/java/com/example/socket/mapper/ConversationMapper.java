package com.example.socket.mapper;

import com.example.socket.entity.ConversationEntity;
import com.example.socket.entity.Customer;
import com.example.socket.entity.Shop;

import java.sql.ResultSet;

public class ConversationMapper implements RowMapper<ConversationEntity>{
    @Override
    public ConversationEntity mapRow(ResultSet rs) {
        ConversationEntity conversationEntity = new ConversationEntity();
        try {
            Shop shop = new Shop();
            shop.setId(rs.getInt("shop_id"));
            shop.setName(rs.getString("shop_name"));
            shop.setAvatar(rs.getString("shop_logo"));
            Customer customer = new Customer();
            customer.setCustomerId(rs.getInt("customer_id"));
            customer.setName(rs.getString("customer_name"));
            customer.setAvatar(rs.getString("customer_avatar"));
            conversationEntity.setId(rs.getInt("id"));
            conversationEntity.setShop(shop);
            conversationEntity.setCustomer(customer);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return conversationEntity;
    }
}
